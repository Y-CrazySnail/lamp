package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car.entity.CarFilmPrice;
import com.yeem.car.entity.CarFilmTenant;
import com.yeem.car.mapper.CarFilmPriceMapper;
import com.yeem.car.service.ICarFilmPriceService;
import com.yeem.car.service.ICarFilmTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.StrUtil;

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
        if (!StrUtil.isEmpty(productNo)) {
            carFilmPriceQueryWrapper.eq("product_no", productNo);
        }
        if (!StrUtil.isEmpty(productLevelNo)) {
            carFilmPriceQueryWrapper.eq("product_level_no", productLevelNo);
        }
        if (!StrUtil.isEmpty(carLevelNo)) {
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
        if (!StrUtil.isEmpty(productNo)) {
            carFilmPriceQueryWrapper.eq("product_no", productNo);
        }
        if (!StrUtil.isEmpty(productLevelNo)) {
            carFilmPriceQueryWrapper.eq("product_level_no", productLevelNo);
        }
        if (!StrUtil.isEmpty(carLevelNo)) {
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
        CarFilmPrice getOneCarFilmPrice = carFilmPriceMapper.selectById(carFilmPrice.getId());
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(getOneCarFilmPrice.getProductNo())) {
            throw new RuntimeException("User does not have permission: illegal deletion!");
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
            throw new RuntimeException("User not authorized: illegal modification");
        }
        carFilmPriceMapper.updateById(carFilmPrice);
    }
}
