package com.yeem.car.service.impl;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.BaseCarBrand;
import com.yeem.car.entity.BaseCarModel;
import com.yeem.car.mapper.CarBrandMapper;
import com.yeem.car.service.ICarBrandService;
import com.yeem.car.service.ICarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CarBrandServiceImpl extends ServiceImpl<CarBrandMapper, BaseCarBrand> implements ICarBrandService {
    @Autowired
    private CarBrandMapper carBrandMapper;
    @Autowired
    private ICarModelService carModelService;


    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public List<BaseCarBrand> list() {
        // 拿到Brand表中没被软删除的
        QueryWrapper<BaseCarBrand> queryWrapper = new QueryWrapper<BaseCarBrand>()
                .eq("delete_flag", 0)
                .orderByAsc("name_en");
        List<BaseCarBrand> baseCarBrandList = carBrandMapper.selectList(queryWrapper);
        // 便利出来
        for (BaseCarBrand baseCarBrand : baseCarBrandList) {
            // 找到对应的数据 放进carBrand中
            baseCarBrand.setCarModelList(carModelService.listByBrandId(baseCarBrand.getId()));
        }
        return baseCarBrandList;
    }

    /**
     * 分页模糊查询
     *
     * @param current
     * @param size
     * @param name
     * @return
     */
    @Override
    public IPage<BaseCarBrand> pages(int current, int size, String name) {
        QueryWrapper<BaseCarBrand> carBrandQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            carBrandQueryWrapper.like("name", name);
        }
        IPage<BaseCarBrand> page = new Page<>(current, size);
        IPage<BaseCarBrand> pages = carBrandMapper.selectPage(page, carBrandQueryWrapper);
        for (BaseCarBrand record : pages.getRecords()) {
            int count = carModelService.count();
            record.setCarModelCount(count);
        }
        return carBrandMapper.selectPage(page, carBrandQueryWrapper);
    }

    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    @Override
    public BaseCarBrand getById(Long id) {
        QueryWrapper<BaseCarBrand> carBrandQueryWrapper = new QueryWrapper<>();
        carBrandQueryWrapper.eq("delete_flag", 0).eq("id", id);
        BaseCarBrand baseCarBrand = carBrandMapper.selectOne(carBrandQueryWrapper);
        if (StringUtils.isEmpty(baseCarBrand)) {
            return null;
        }
        List<BaseCarModel> baseCarModelist = carModelService.listByBrandId(id);
        baseCarBrand.setCarModelList(baseCarModelist);
        return baseCarBrand;
    }

    /**
     * 软删除
     *
     * @param baseCarBrand
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void remove(BaseCarBrand baseCarBrand) {
        carModelService.removeByBrandId(baseCarBrand.getId());
        baseCarBrand.setDeleteFlag(true);
        carBrandMapper.updateById(baseCarBrand);
    }

    /**
     * 更改
     *
     * @param baseCarBrand
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(BaseCarBrand baseCarBrand) {
        baseCarBrand.setNameEn(PinyinUtil.getPinyin(baseCarBrand.getName()));
        carBrandMapper.updateById(baseCarBrand);
        // 数据库-车型
        List<BaseCarModel> databaseBaseCarModelList = this.getById(baseCarBrand.getId()).getCarModelList();
        // 前端-车型
        List<BaseCarModel> baseCarModelList = baseCarBrand.getCarModelList();
        baseCarModelList.forEach(carModel -> {
            carModel.setBrandId(baseCarBrand.getId());
            if (StringUtils.isEmpty(carModel.getId())) {
                carModelService.save(carModel);
            } else {
                carModelService.updateById(carModel);
            }
        });
        databaseBaseCarModelList.forEach(databaseCarModel -> {
            boolean exist = baseCarModelList.stream().filter(carModel -> carModel.getId() != null).anyMatch(carModel -> databaseCarModel.getId().equals(carModel.getId()));
            if (!exist) {
                carModelService.remove(databaseCarModel.getId());
            }
        });
    }

    /**
     * 新增
     *
     * @param baseCarBrand
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(BaseCarBrand baseCarBrand) {
        baseCarBrand.setNameEn(PinyinUtil.getPinyin(baseCarBrand.getName()));
        carBrandMapper.insert(baseCarBrand);
        return carModelService.save(baseCarBrand.getCarModelList(), baseCarBrand.getId());
    }
}
