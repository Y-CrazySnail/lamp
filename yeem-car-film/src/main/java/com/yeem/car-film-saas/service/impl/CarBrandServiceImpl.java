package com.yeem.car_film_saas.service.impl;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car_film_saas.entity.CarBrand;
import com.yeem.car_film_saas.entity.CarModel;
import com.yeem.car_film_saas.mapper.CarBrandMapper;
import com.yeem.car_film_saas.service.ICarBrandService;
import com.yeem.car_film_saas.service.ICarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CarBrandServiceImpl extends ServiceImpl<CarBrandMapper, CarBrand> implements ICarBrandService {
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
    public List<CarBrand> list() {
        // 拿到Brand表中没被软删除的
        List<CarBrand> carBrandList = carBrandMapper.selectList(new QueryWrapper<CarBrand>().eq("delete_flag", 0));
        // 便利出来
        for (CarBrand carBrand : carBrandList) {
            // 找到对应的数据 放进carBrand中
            carBrand.setCarModelList(carModelService.listByBrandId(carBrand.getId()));
        }
        return carBrandList;
    }

    /**
     * 分页模糊查询
     *
     * @param current
     * @param size
     * @param brandName
     * @return
     */
    @Override
    public IPage<CarBrand> page(int current, int size, String brandName) {
        QueryWrapper<CarBrand> carBrandQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(brandName)) {
            carBrandQueryWrapper.like("name", brandName);
        }
        IPage<CarBrand> page = new Page<>(current, size);
        return carBrandMapper.selectPage(page, carBrandQueryWrapper);
    }

    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    @Override
    public CarBrand getById(Long id) {
        QueryWrapper<CarBrand> carBrandQueryWrapper = new QueryWrapper<>();
        carBrandQueryWrapper.eq("delete_flag", 0).eq("id", id);
        CarBrand carBrand = carBrandMapper.selectOne(carBrandQueryWrapper);
        if (StringUtils.isEmpty(carBrand)) {
            return null;
        }
        List<CarModel> carModelist = carModelService.listByBrandId(id);
        carBrand.setCarModelList(carModelist);
        return carBrand;
    }

    /**
     * 软删除
     *
     * @param carBrand
     */
    @Override
    public void remove(CarBrand carBrand) {
        carModelService.removeByBrandId(carBrand.getId());
        carBrand.setDeleteFlag(true);
        carBrandMapper.updateById(carBrand);
    }

    /**
     * 更改
     *
     * @param carBrand
     */
    @Override
    public void update(CarBrand carBrand) {
        carBrand.setNameEn(PinyinUtil.getPinyin(carBrand.getName()));
        carBrand.setLogoName(FileNameUtil.getName(carBrand.getLogoPath()));
        carBrandMapper.updateById(carBrand);
        // 数据库-车型
        List<CarModel> databaseCarModelList = this.getById(carBrand.getId()).getCarModelList();
        // 前端-车型
        List<CarModel> carModelList = carBrand.getCarModelList();
        carModelList.forEach(carModel -> {
            carModel.setBrandId(carBrand.getId());
            if (StringUtils.isEmpty(carModel.getId())) {
                carModelService.save(carModel);
            } else {
                carModelService.updateById(carModel);
            }
        });
        databaseCarModelList.forEach(databaseCarModel -> {
            boolean exist = carModelList.stream().filter(carModel -> carModel.getId() != null).anyMatch(carModel -> databaseCarModel.getId().equals(carModel.getId()));
            if (!exist) {
                carModelService.remove(databaseCarModel.getId());
            }
        });
        // 非 Java8 写法
        // for (CarModel databaseCarModel : databaseCarModelList) {
        //     boolean flag = false;
        //     for (CarModel carModel : carModelList) {
        //         if (null != carModel.getId() && databaseCarModel.getId().equals(carModel.getId())) {
        //             flag = true;
        //             break;
        //         }
        //     }
        //     if (!flag) {
        //         carModelService.remove(databaseCarModel.getId());
        //     }
        // }
    }

    /**
     * 新增
     *
     * @param carBrand
     * @return
     */
    @Override
    public boolean save(CarBrand carBrand) {
        carBrand.setNameEn(PinyinUtil.getPinyin(carBrand.getName()));
        carBrand.setLogoName(FileNameUtil.getName(carBrand.getLogoPath()));
        carBrandMapper.insert(carBrand);
        return carModelService.save(carBrand.getCarModelList(), carBrand.getId());
    }
}
