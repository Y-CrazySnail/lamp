package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.snail.car_film_saas.entity.CarFilmProduct;
import com.snail.car_film_saas.entity.CarFilmQuality;
import com.snail.car_film_saas.mapper.CarFilmQualityMapper;
import com.snail.car_film_saas.service.ICarFilmQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CarFilmQualityServiceImpl extends ServiceImpl<CarFilmQualityMapper, CarFilmQuality> implements ICarFilmQualityService {

    @Autowired
    private CarFilmQualityMapper carFilmQualityMapper;

    @Override
    public List<CarFilmQuality> list(String name, String productNo, String phone, String qualityCardNo, String plateNo, String vin, String likePhone, String likeQualityCardNo, String likePlateNo, String likeVin) {
        QueryWrapper<CarFilmQuality> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(phone)) {
            wrapper.eq("phone", phone);
        }
        if (!StringUtils.isEmpty(qualityCardNo)) {
            wrapper.eq("quality_card_no", qualityCardNo);
        }
        if (!StringUtils.isEmpty(plateNo)) {
            wrapper.eq("plate_no", plateNo);
        }
        if (!StringUtils.isEmpty(vin)) {
            wrapper.eq("vin", vin);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(likePhone)) {
            wrapper.like("phone", likePhone);
        }
        if (!StringUtils.isEmpty(likeQualityCardNo)) {
            wrapper.like("quality_card_no", likeQualityCardNo);
        }
        if (!StringUtils.isEmpty(likePlateNo)) {
            wrapper.like("plate_no", plateNo);
        }
        if (!StringUtils.isEmpty(likeVin)) {
            wrapper.like("vin", likeVin);
        }
        wrapper.eq("delete_flag", 0);
        return carFilmQualityMapper.selectList(wrapper);
    }


    @Override
    public IPage<CarFilmQuality> pages(int current, int size, String name, String productNo, String phone, String qualityCardNo, String plateNo, String vin, String likePhone, String likeQualityCardNo, String likePlateNo, String likeVin) {
        QueryWrapper<CarFilmQuality> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(phone)) {
            wrapper.eq("phone", phone);
        }
        if (!StringUtils.isEmpty(qualityCardNo)) {
            wrapper.eq("quality_card_no", qualityCardNo);
        }
        if (!StringUtils.isEmpty(plateNo)) {
            wrapper.eq("plate_no", plateNo);
        }
        if (!StringUtils.isEmpty(vin)) {
            wrapper.eq("vin", vin);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(likePhone)) {
            wrapper.like("phone", likePhone);
        }
        if (!StringUtils.isEmpty(likeQualityCardNo)) {
            wrapper.like("quality_card_no", likeQualityCardNo);
        }
        if (!StringUtils.isEmpty(likePlateNo)) {
            wrapper.like("plate_no", plateNo);
        }
        if (!StringUtils.isEmpty(likeVin)) {
            wrapper.like("vin", likeVin);
        }
        wrapper.eq("delete_flag", 0);
        Page<CarFilmQuality> page = new Page<>(current, size);
        return carFilmQualityMapper.selectPage(page, wrapper);
    }

    @Override
    public CarFilmQuality getById(Long id) {
        return carFilmQualityMapper.selectOne(new QueryWrapper<CarFilmQuality>().eq("delete_flag", 0).eq("id", id));
    }

    @Override
    public void remove(CarFilmQuality carFilmQuality) {
        carFilmQuality.setDeleteFlag(true);
        carFilmQualityMapper.updateById(carFilmQuality);
    }

    @Override
    public boolean save(CarFilmQuality carFilmQuality) {
        return SqlHelper.retBool(carFilmQualityMapper.insert(carFilmQuality));
    }

    @Override
    public void update(CarFilmQuality carFilmQuality) {
        carFilmQualityMapper.updateById(carFilmQuality);
    }
}
