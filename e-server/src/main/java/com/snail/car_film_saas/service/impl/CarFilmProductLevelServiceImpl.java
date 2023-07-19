package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.snail.car_film_saas.entity.CarFilmProductLevel;
import com.snail.car_film_saas.mapper.CarFilmProductLevelMapper;
import com.snail.car_film_saas.service.ICarFilmProductLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CarFilmProductLevelServiceImpl extends ServiceImpl<CarFilmProductLevelMapper, CarFilmProductLevel> implements ICarFilmProductLevelService {

    @Autowired
    private CarFilmProductLevelMapper carFilmProductLevelMapper;

    @Override
    public List<CarFilmProductLevel> list(String productNo, String productLevelName, String status) {
        QueryWrapper<CarFilmProductLevel> wrapper = new QueryWrapper<>();
        if (StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (StringUtils.isEmpty(productLevelName)) {
            wrapper.like("product_level_name", productLevelName);
        }
        if (StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        wrapper.eq("delete_flag", 0);
        return carFilmProductLevelMapper.selectList(wrapper);
    }

    @Override
    public IPage<CarFilmProductLevel> pages(int current, int size, String productNo, String productLevelName, String status) {
        QueryWrapper<CarFilmProductLevel> wrapper = new QueryWrapper<>();
        if (StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (StringUtils.isEmpty(productLevelName)) {
            wrapper.like("product_level_name", productLevelName);
        }
        if (StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        wrapper.eq("delete_flag", 0);
        Page<CarFilmProductLevel> page = new Page<>(current, size);
        return carFilmProductLevelMapper.selectPage(page, wrapper);
    }

    @Override
    public CarFilmProductLevel getById(Long id) {
        return carFilmProductLevelMapper.selectOne(new QueryWrapper<CarFilmProductLevel>().eq("delete_flag", 0).eq("id", id));
    }

    @Override
    public void remove(CarFilmProductLevel carFilmProductLevel) {
        carFilmProductLevel.setDeleteFlag(true);
        carFilmProductLevelMapper.updateById(carFilmProductLevel);
    }

    @Override
    public boolean save(CarFilmProductLevel carFilmProductLevel) {
        return SqlHelper.retBool(carFilmProductLevelMapper.insert(carFilmProductLevel));
    }

    @Override
    public void update(CarFilmProductLevel carFilmProductLevel) {
        carFilmProductLevelMapper.updateById(carFilmProductLevel);
    }
}
