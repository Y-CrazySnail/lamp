package com.yeem.car.film.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car.film.entity.CarFilmPrice;
import com.yeem.car.film.entity.CarFilmProduct;
import com.yeem.car.film.mapper.CarFilmProductMapper;
import com.yeem.car.film.service.ICarFilmPriceService;
import com.yeem.car.film.service.ICarFilmProductService;
import com.yeem.car.film.service.ICarFilmTenantService;
import com.yeem.car.film.entity.CarFilmTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarFilmProductServiceImpl extends ServiceImpl<CarFilmProductMapper, CarFilmProduct> implements ICarFilmProductService {

    @Autowired
    private CarFilmProductMapper carFilmProductMapper;
    @Autowired
    private ICarFilmTenantService carFilmTenantService;
    @Autowired
    private ICarFilmPriceService carFilmPriceService;

    @Override
    public List<CarFilmProduct> list(String productNo, String productLevelName, String status) {
        QueryWrapper<CarFilmProduct> wrapper = new QueryWrapper<>();
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        wrapper.in("product_no", carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()));
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(productLevelName)) {
            wrapper.like("product_level_name", productLevelName);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        wrapper.eq("delete_flag", 0);
        return carFilmProductMapper.selectList(wrapper);
    }

    @Override
    public List<CarFilmProduct> listWithPrice(String productNo) {
        QueryWrapper<CarFilmProduct> carFilmProductQueryWrapper = new QueryWrapper<>();
        carFilmProductQueryWrapper.eq("product_no", productNo);
        List<CarFilmProduct> carFilmProductList = carFilmProductMapper.selectList(carFilmProductQueryWrapper);
        for (CarFilmProduct carFilmProduct : carFilmProductList) {
            QueryWrapper<CarFilmPrice> carFilmPriceQueryWrapper = new QueryWrapper<>();
            carFilmPriceQueryWrapper.eq("product_level_no", carFilmProduct.getProductLevelNo());
            carFilmPriceQueryWrapper.eq("product_no", carFilmProduct.getProductNo());
            List<CarFilmPrice> carFilmPriceList = carFilmPriceService.list(carFilmPriceQueryWrapper);
            carFilmProduct.setCarFilmPriceList(carFilmPriceList);
        }
        return carFilmProductList;
    }

    @Override
    public IPage<CarFilmProduct> pages(int current,
                                       int size,
                                       String productNo,
                                       String productLevelName,
                                       String status) {
        QueryWrapper<CarFilmProduct> wrapper = new QueryWrapper<>();
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        wrapper.in("product_no", carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()));
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(productLevelName)) {
            wrapper.like("product_level_name", productLevelName);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        wrapper.eq("delete_flag", 0);
        Page<CarFilmProduct> page = new Page<>(current, size);
        return carFilmProductMapper.selectPage(page, wrapper);
    }

    @Override
    public CarFilmProduct getById(Long id) {
        return carFilmProductMapper.selectOne(new QueryWrapper<CarFilmProduct>().eq("delete_flag", 0).eq("id", id));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void remove(CarFilmProduct carFilmProductLevel) {
        CarFilmProduct getOneCarFilmProduct = carFilmProductMapper.selectById(carFilmProductLevel.getId());
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(getOneCarFilmProduct.getProductNo())) {
            throw new RuntimeException("User does not have permission: illegal deletion!");
        }
        carFilmProductLevel.setDeleteFlag(true);
        carFilmProductMapper.updateById(carFilmProductLevel);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(CarFilmProduct carFilmProductLevel) {
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(carFilmProductLevel.getProductNo())) {
            return false;
        }
        return SqlHelper.retBool(carFilmProductMapper.insert(carFilmProductLevel));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(CarFilmProduct carFilmProductLevel) {
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(carFilmProductLevel.getProductNo())) {
            throw new RuntimeException("User not authorized: illegal modification");
        }
        carFilmProductMapper.updateById(carFilmProductLevel);
    }
}
