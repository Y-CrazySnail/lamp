package com.yeem.car.film.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.film.entity.CarFilmQuality;

import java.util.List;

public interface ICarFilmQualityService extends IService<CarFilmQuality> {

    List<CarFilmQuality> list(String name, String productNo, String phone, String qualityCardNo, String plateNo, String vin, String likeName, String likePhone, String likeQualityCardNo, String likePlateNo, String likeVin);

    IPage<CarFilmQuality> pages(int current, int size, String name, String productNo, String phone, String qualityCardNo, String plateNo, String vin);

    CarFilmQuality getById(Long id);

    void remove(CarFilmQuality carFilmQuality);


    boolean save(CarFilmQuality carFilmQuality);


    void update(CarFilmQuality carFilmQuality);

    List<CarFilmQuality> getQualityInfo(String productNo, String phone);
}
