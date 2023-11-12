package com.yeem.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car_film_saas.entity.CarFilmPrice;
import com.yeem.car_film_saas.entity.CarFilmTenant;
import com.yeem.car_film_saas.mapper.CarFilmPriceMapper;
import com.yeem.car_film_saas.service.ICarFilmPriceService;
import com.yeem.car_film_saas.service.ICarFilmTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarFilmPriceServiceImpl extends ServiceImpl<CarFilmPriceMapper, CarFilmPrice> implements ICarFilmPriceService {

    @Autowired
    private CarFilmPriceMapper carFilmPriceMapper;
    @Autowired
    private ICarFilmTenantService carFilmTenantService;

    @Override
    public List<CarFilmPrice> list(String productNo, String productLevelNo, String carLevelNo) {
        QueryWrapper<CarFilmPrice> carFilmPriceQueryWrapper = new QueryWrapper<>();
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        carFilmPriceQueryWrapper.in("product_no", carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()));
        if (!StringUtils.isEmpty(productNo)) {
            carFilmPriceQueryWrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(productLevelNo)) {
            carFilmPriceQueryWrapper.eq("product_level_no", productLevelNo);
        }
        if (!StringUtils.isEmpty(carLevelNo)) {
            carFilmPriceQueryWrapper.eq("car_level_no", carLevelNo);
        }
        carFilmPriceQueryWrapper.eq("delete_flag", 0);
        return carFilmPriceMapper.selectList(carFilmPriceQueryWrapper);
    }

    @Override
    public IPage<CarFilmPrice> pages(int current, int size, String productNo, String productLevelNo, String carLevelNo) {
        QueryWrapper<CarFilmPrice> carFilmPriceQueryWrapper = new QueryWrapper<>();
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        carFilmPriceQueryWrapper.in("product_no", carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()));
        if (!StringUtils.isEmpty(productNo)) {
            carFilmPriceQueryWrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(productLevelNo)) {
            carFilmPriceQueryWrapper.eq("product_level_no", productLevelNo);
        }
        if (!StringUtils.isEmpty(carLevelNo)) {
            carFilmPriceQueryWrapper.eq("car_level_no", carLevelNo);
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
    @Transactional(rollbackFor = {Exception.class})
    public void remove(CarFilmPrice carFilmPrice) {
        CarFilmPrice getOneCarFilmPrice = carFilmPriceMapper.selectOne(new QueryWrapper<CarFilmPrice>().eq("id", carFilmPrice.getId()));
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(getOneCarFilmPrice.getProductNo())) {
            throw new RuntimeException("那里不可以哦~ :)");
        }
        carFilmPrice.setDeleteFlag(true);
        carFilmPriceMapper.updateById(carFilmPrice);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(CarFilmPrice carFilmPrice) {
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(carFilmPrice.getProductNo())) {
            return false;
        }
        return SqlHelper.retBool(carFilmPriceMapper.insert(carFilmPrice));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(CarFilmPrice carFilmPrice) {
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(carFilmPrice.getProductNo())) {
            throw new RuntimeException("nonono");
        }
        carFilmPriceMapper.updateById(carFilmPrice);
    }
}
