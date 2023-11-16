package com.yeem.car.film.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car.film.entity.CarFilmTechnician;
import com.yeem.car.film.mapper.CarFilmTechnicianMapper;
import com.yeem.car.film.entity.CarFilmTenant;
import com.yeem.car.film.service.ICarFilmTechnicianService;
import com.yeem.car.film.service.ICarFilmTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarFilmTechnicianServiceImpl extends ServiceImpl<CarFilmTechnicianMapper, CarFilmTechnician> implements ICarFilmTechnicianService {
    @Autowired
    private CarFilmTechnicianMapper carFilmTechnicianMapper;

    @Autowired
    private ICarFilmTenantService carFilmTenantService;

    @Override
    public List<CarFilmTechnician> list(String productNo, String name, String province, String city, String county, String level) {
        QueryWrapper<CarFilmTechnician> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(province)) {
            wrapper.eq("province", province);
        }
        if (!StringUtils.isEmpty(city)) {
            wrapper.eq("city", city);
        }
        if (!StringUtils.isEmpty(county)) {
            wrapper.eq("county", county);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        wrapper.eq("delete_flag", 0);
        return carFilmTechnicianMapper.selectList(wrapper);
    }

    @Override
    public IPage<CarFilmTechnician> pages(int current, int size, String productNo, String name, String province, String city, String county, String level) {
        QueryWrapper<CarFilmTechnician> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(province)) {
            wrapper.eq("province", province);
        }
        if (!StringUtils.isEmpty(city)) {
            wrapper.eq("city", city);
        }
        if (!StringUtils.isEmpty(county)) {
            wrapper.eq("county", county);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        wrapper.eq("delete_flag", 0);
        Page<CarFilmTechnician> page = new Page<>(current, size);
        return carFilmTechnicianMapper.selectPage(page, wrapper);
    }

    @Override
    public CarFilmTechnician getById(Long id) {
        return carFilmTechnicianMapper.selectOne(new QueryWrapper<CarFilmTechnician>().eq("id", id).eq("delete_flag", 0));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void remove(CarFilmTechnician carFilmTechnician) {
        CarFilmTechnician getOneCarCarFilmTechnician = carFilmTechnicianMapper.selectById(carFilmTechnician.getId());
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(getOneCarCarFilmTechnician.getProductNo())) {
            throw new RuntimeException("User does not have permission: illegal deletion!");
        }
        carFilmTechnician.setDeleteFlag(true);
        carFilmTechnicianMapper.updateById(carFilmTechnician);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(CarFilmTechnician carFilmTechnician) {
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(carFilmTechnician.getProductNo())) {
            return false;
        }
        return SqlHelper.retBool(carFilmTechnicianMapper.insert(carFilmTechnician));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(CarFilmTechnician carFilmTechnician) {
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(carFilmTechnician.getProductNo())) {
            throw new RuntimeException("User not authorized: illegal modification");
        }
        carFilmTechnicianMapper.updateById(carFilmTechnician);
    }
}
