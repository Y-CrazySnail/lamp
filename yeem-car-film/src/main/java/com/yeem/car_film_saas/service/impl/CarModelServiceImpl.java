package com.yeem.car_film_saas.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car_film_saas.entity.CarLevel;
import com.yeem.car_film_saas.entity.CarModel;
import com.yeem.car_film_saas.mapper.CarModelMapper;
import com.yeem.car_film_saas.service.ICarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

@Service
public class CarModelServiceImpl extends ServiceImpl<CarModelMapper, CarModel> implements ICarModelService {
    @Autowired
    private CarModelMapper carModelMapper;
    @Autowired
    private CarLevelServiceImpl carLevelService;

    @Autowired
    CarBrandServiceImpl carBrandService;

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public List<CarModel> list() {
        List<CarModel> carModels = carModelMapper.selectList(new QueryWrapper<CarModel>().eq("delete_flag", 0));
        List<CarLevel> carLevels = carLevelService.list();
        for (CarModel carModel : carModels) {
            for (CarLevel carLevel : carLevels) {
                if (carLevel.getLevelNo().equals(carModel.getLevelNo())) {
                    carModel.setLevelName(carLevel.getLevelName());
                }
            }
        }
        return carModels;
    }

    /**
     * Brand_Id查询所有不被软删除的数据
     *
     * @param id
     * @return
     */
    @Override
    public List<CarModel> listByBrandId(Long id) {
        List<CarModel> carModels = carModelMapper.selectList(new QueryWrapper<CarModel>().eq("brand_id", id).eq("delete_flag", 0));
        List<CarLevel> carLevels = carLevelService.list();
        for (CarModel carModel : carModels) {
            for (CarLevel carLevel : carLevels) {
                if (carLevel.getLevelNo().equals(carModel.getLevelNo())) {
                    carModel.setLevelName(carLevel.getLevelName());
                }
            }
        }
        return carModels;
    }

    /**
     * 分页模糊查询
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public IPage<CarModel> pages(int current, int size, String name) {
        QueryWrapper<CarModel> carModelQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            carModelQueryWrapper.like("name", name);
        }
        carModelQueryWrapper.eq("delete_flag", 0);
        Page<CarModel> page = new Page<>(current, size);
        Page<CarModel> carModelPage = carModelMapper.selectPage(page, carModelQueryWrapper);
        List<CarModel> records = carModelPage.getRecords();
        List<CarLevel> carLevels = carLevelService.list();
        for (CarModel carModel : records) {
            for (CarLevel carLevel : carLevels) {
                if (carLevel.getLevelNo().equals(carModel.getLevelNo())) {
                    carModel.setLevelName(carLevel.getLevelName());
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
    public CarModel getById(Long id) {
        CarModel carModel = carModelMapper.selectById(id);
        for (CarLevel carLevel : carLevelService.list()) {
            if (carLevel.getLevelNo().equals(carModel.getLevelNo())) {
                carModel.setLevelName(carLevel.getLevelName());
            }
        }
        return carModel;
    }

    /**
     * 软删除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void removeByBrandId(Long id) {
        UpdateWrapper<CarModel> wrapper = new UpdateWrapper<>();
        wrapper.eq("brand_id", id).set("delete_flag", true);
        carModelMapper.update(null, wrapper);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void remove(Long id) {
        UpdateWrapper<CarModel> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("delete_flag", true);
        carModelMapper.update(null, wrapper);
    }

    /**
     * 新增
     *
     * @param carModelList
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(List<CarModel> carModelList, Long brandId) {
        boolean flag = false;
        for (CarModel carModel : carModelList) {
            carModel.setBrandId(brandId);
            carModel.setNameEn(PinyinUtil.getPinyin(carModel.getName()));
            flag = SqlHelper.retBool(carModelMapper.insert(carModel));
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void insert(CarModel carModel) {
        carModel.setNameEn(PinyinUtil.getPinyin(carModel.getName()));
        carModelMapper.insert(carModel);
    }

    /**
     * 更新
     *
     * @param carModelList
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(List<CarModel> carModelList) {
        for (CarModel carModel : carModelList) {
            carModel.setNameEn(PinyinUtil.getPinyin(carModel.getName()));
            carModelMapper.updateById(carModel);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(CarModel carModel) {
        carModel.setNameEn(PinyinUtil.getPinyin(carModel.getName()));
        return super.save(carModel);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateById(CarModel carModel) {
        carModel.setNameEn(PinyinUtil.getPinyin(carModel.getName()));
        return super.updateById(carModel);
    }
}
