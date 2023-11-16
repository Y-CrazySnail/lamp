package com.yeem.car.film.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.film.entity.CarFilmStore;

import java.util.List;

public interface ICarFilmStoreService extends IService<CarFilmStore> {

    List<CarFilmStore> list(String productNo, String name, String province, String city, String county, String contactName,String contactPhone);

    IPage<CarFilmStore> pages(int current, int size, String productNo, String name, String province, String city, String county, String contactName,String contactPhone);

    CarFilmStore getById(Long id);

    void remove(CarFilmStore carFilmStore);


    boolean save(CarFilmStore carFilmStore);


    void update(CarFilmStore carFilmStore);
}
