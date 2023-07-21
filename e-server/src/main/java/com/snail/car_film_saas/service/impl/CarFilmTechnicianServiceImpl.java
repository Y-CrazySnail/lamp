package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.snail.car_film_saas.entity.CarFilmTechnician;
import com.snail.car_film_saas.mapper.CarFilmTechnicianMapper;
import com.snail.car_film_saas.service.ICarFilmTechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CarFilmTechnicianServiceImpl extends ServiceImpl<CarFilmTechnicianMapper, CarFilmTechnician> implements ICarFilmTechnicianService {
    @Autowired
    private CarFilmTechnicianMapper carFilmTechnicianMapper;

    @Override
    public List<CarFilmTechnician> list(String productNo, String name, String province, String city, String county, String level) {
        QueryWrapper<CarFilmTechnician> wrapper = new QueryWrapper<>();
        if (StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (StringUtils.isEmpty(province)) {
            wrapper.eq("province", province);
        }
        if (StringUtils.isEmpty(city)) {
            wrapper.eq("city", city);
        }
        if (StringUtils.isEmpty(county)) {
            wrapper.eq("county", county);
        }
        if (StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        wrapper.eq("delete_flag", 0);
        return carFilmTechnicianMapper.selectList(wrapper);
    }

    @Override
    public IPage<CarFilmTechnician> pages(int current, int size, String productNo, String name, String province, String city, String county, String level) {
        QueryWrapper<CarFilmTechnician> wrapper = new QueryWrapper<>();
        if (StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (StringUtils.isEmpty(province)) {
            wrapper.eq("province", province);
        }
        if (StringUtils.isEmpty(city)) {
            wrapper.eq("city", city);
        }
        if (StringUtils.isEmpty(county)) {
            wrapper.eq("county", county);
        }
        if (StringUtils.isEmpty(level)) {
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
    public void remove(CarFilmTechnician carFilmTechnician) {
        carFilmTechnician.setDeleteFlag(true);
        carFilmTechnicianMapper.updateById(carFilmTechnician);
    }

    @Override
    public boolean save(CarFilmTechnician carFilmTechnician) {
        return SqlHelper.retBool(carFilmTechnicianMapper.insert(carFilmTechnician));
    }

    @Override
    public void update(CarFilmTechnician carFilmTechnician) {
        carFilmTechnicianMapper.updateById(carFilmTechnician);
    }
}
