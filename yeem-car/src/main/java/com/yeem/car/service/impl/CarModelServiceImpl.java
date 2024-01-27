package com.yeem.car.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car.entity.BaseCarLevel;
import com.yeem.car.entity.BaseCarModel;
import com.yeem.car.mapper.CarModelMapper;
import com.yeem.car.service.ICarBrandService;
import com.yeem.car.service.ICarLevelService;
import com.yeem.car.service.ICarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CarModelServiceImpl extends ServiceImpl<CarModelMapper, BaseCarModel> implements ICarModelService {
    @Autowired
    private CarModelMapper carModelMapper;

    @Autowired
    private ICarLevelService carLevelService;

    @Autowired
    private ICarBrandService carBrandService;

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public List<BaseCarModel> list() {
        List<BaseCarModel> baseCarModels = carModelMapper.selectList(new QueryWrapper<BaseCarModel>().eq("delete_flag", 0));
        List<BaseCarLevel> baseCarLevels = carLevelService.list();
        for (BaseCarModel baseCarModel : baseCarModels) {
            for (BaseCarLevel baseCarLevel : baseCarLevels) {
                if (baseCarLevel.getLevelNo().equals(baseCarModel.getLevelNo())) {
                    baseCarModel.setLevelName(baseCarLevel.getLevelName());
                }
            }
        }
        return baseCarModels;
    }

    /**
     * Brand_Id查询所有不被软删除的数据
     *
     * @param id
     * @return
     */
    @Override
    public List<BaseCarModel> listByBrandId(Long id) {
        QueryWrapper<BaseCarModel> queryWrapper = new QueryWrapper<BaseCarModel>()
                .eq("brand_id", id)
                .eq("delete_flag", 0)
                .orderByAsc("name_en");
        List<BaseCarModel> baseCarModels = carModelMapper.selectList(queryWrapper);
        List<BaseCarLevel> baseCarLevels = carLevelService.list();
        for (BaseCarModel baseCarModel : baseCarModels) {
            for (BaseCarLevel baseCarLevel : baseCarLevels) {
                if (baseCarLevel.getLevelNo().equals(baseCarModel.getLevelNo())) {
                    baseCarModel.setLevelName(baseCarLevel.getLevelName());
                }
            }
        }
        return baseCarModels;
    }

    /**
     * 分页模糊查询
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public IPage<BaseCarModel> pages(int current, int size, String name) {
        QueryWrapper<BaseCarModel> carModelQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            carModelQueryWrapper.like("name", name);
        }
        carModelQueryWrapper.eq("delete_flag", 0);
        Page<BaseCarModel> page = new Page<>(current, size);
        Page<BaseCarModel> carModelPage = carModelMapper.selectPage(page, carModelQueryWrapper);
        List<BaseCarModel> records = carModelPage.getRecords();
        List<BaseCarLevel> baseCarLevels = carLevelService.list();
        for (BaseCarModel baseCarModel : records) {
            for (BaseCarLevel baseCarLevel : baseCarLevels) {
                if (baseCarLevel.getLevelNo().equals(baseCarModel.getLevelNo())) {
                    baseCarModel.setLevelName(baseCarLevel.getLevelName());
                }
            }
        }
        return carModelPage;
    }

    /**
     * 单独查id
     *
     * @param id
     * @return
     */
    @Override
    public BaseCarModel getById(Long id) {
        BaseCarModel baseCarModel = carModelMapper.selectById(id);
        for (BaseCarLevel baseCarLevel : carLevelService.list()) {
            if (baseCarLevel.getLevelNo().equals(baseCarModel.getLevelNo())) {
                baseCarModel.setLevelName(baseCarLevel.getLevelName());
            }
        }
        return baseCarModel;
    }

    /**
     * 软删除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void removeByBrandId(Long id) {
        UpdateWrapper<BaseCarModel> wrapper = new UpdateWrapper<>();
        wrapper.eq("brand_id", id).set("delete_flag", true);
        carModelMapper.update(null, wrapper);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void remove(Long id) {
        UpdateWrapper<BaseCarModel> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("delete_flag", true);
        carModelMapper.update(null, wrapper);
    }

    /**
     * 新增
     *
     * @param baseCarModelList
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(List<BaseCarModel> baseCarModelList, Long brandId) {
        boolean flag = false;
        for (BaseCarModel baseCarModel : baseCarModelList) {
            baseCarModel.setBrandId(brandId);
            baseCarModel.setNameEn(PinyinUtil.getPinyin(baseCarModel.getName()));
            flag = SqlHelper.retBool(carModelMapper.insert(baseCarModel));
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void insert(BaseCarModel baseCarModel) {
        baseCarModel.setNameEn(PinyinUtil.getPinyin(baseCarModel.getName()));
        carModelMapper.insert(baseCarModel);
    }

    /**
     * 更新
     *
     * @param baseCarModelList
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(List<BaseCarModel> baseCarModelList) {
        for (BaseCarModel baseCarModel : baseCarModelList) {
            baseCarModel.setNameEn(PinyinUtil.getPinyin(baseCarModel.getName()));
            carModelMapper.updateById(baseCarModel);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(BaseCarModel baseCarModel) {
        baseCarModel.setNameEn(PinyinUtil.getPinyin(baseCarModel.getName()));
        return super.save(baseCarModel);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateById(BaseCarModel baseCarModel) {
        baseCarModel.setNameEn(PinyinUtil.getPinyin(baseCarModel.getName()));
        return super.updateById(baseCarModel);
    }
}
