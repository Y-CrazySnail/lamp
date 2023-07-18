package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.snail.car_film_saas.entity.CarFilmPrice;
import com.snail.car_film_saas.mapper.CarFilmPriceMapper;
import com.snail.car_film_saas.service.ICarFilmPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CarFilmPriceServiceImpl extends ServiceImpl<CarFilmPriceMapper, CarFilmPrice> implements ICarFilmPriceService {

    @Autowired
    private CarFilmPriceMapper carFilmPriceMapper;

    @Override
    public List<CarFilmPrice> list(CarFilmPrice carFilmPrice) {
        QueryWrapper<CarFilmPrice> carFilmPriceQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isEmpty(carFilmPrice.getProductNo())) {
            carFilmPriceQueryWrapper.eq("product_no", carFilmPrice.getProductNo());
        }
        if (StringUtils.isEmpty(carFilmPrice.getProductLevelNo())) {
            carFilmPriceQueryWrapper.eq("product_level_no", carFilmPrice.getProductLevelNo());
        }
        if (StringUtils.isEmpty(carFilmPrice.getCarLevelNo())) {
            carFilmPriceQueryWrapper.eq("car_level_no", carFilmPrice.getCarLevelNo());
        }
        carFilmPriceQueryWrapper.eq("delete_flag", 0);
        return carFilmPriceMapper.selectList(carFilmPriceQueryWrapper);
    }

    @Override
    public IPage<CarFilmPrice> pages(int current, int size, CarFilmPrice carFilmPrice) {
        QueryWrapper<CarFilmPrice> carFilmPriceQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isEmpty(carFilmPrice.getProductNo())) {
            carFilmPriceQueryWrapper.eq("product_no", carFilmPrice.getProductNo());
        }
        if (StringUtils.isEmpty(carFilmPrice.getProductLevelNo())) {
            carFilmPriceQueryWrapper.eq("product_level_no", carFilmPrice.getProductLevelNo());
        }
        if (StringUtils.isEmpty(carFilmPrice.getCarLevelNo())) {
            carFilmPriceQueryWrapper.eq("car_level_no", carFilmPrice.getCarLevelNo());
        }
        carFilmPriceQueryWrapper.eq("delete_flag", 0);
        Page<CarFilmPrice> page = new Page<>(current, size);
        return carFilmPriceMapper.selectPage(page, carFilmPriceQueryWrapper);
    }

    @Override
    public CarFilmPrice getById(Long id) {
        return carFilmPriceMapper.selectOne(new QueryWrapper<CarFilmPrice>().eq("delete_flag", 0).eq("id", id));
    }

    @Override
    public void remove(CarFilmPrice carFilmPrice) {
        carFilmPrice.setDeleteFlag(true);
        carFilmPriceMapper.updateById(carFilmPrice);
    }

    @Override
    public boolean save(CarFilmPrice carFilmPrice) {
        return SqlHelper.retBool(carFilmPriceMapper.insert(carFilmPrice));
    }

    @Override
    public void update(CarFilmPrice carFilmPrice) {
        carFilmPriceMapper.updateById(carFilmPrice);
    }
}
