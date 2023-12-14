package com.yeem.car.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.CarFilmTechnician;

import java.util.List;

public interface ICarFilmTechnicianService extends IService<CarFilmTechnician> {

    List<CarFilmTechnician> list(String productNo, String name, String province, String city, String county, String level);

    IPage<CarFilmTechnician> pages(int current, int size, String productNo, String name, String province, String city, String county, String level);

    CarFilmTechnician getById(Long id);

    void remove(CarFilmTechnician carFilmTechnician);


    boolean save(CarFilmTechnician carFilmTechnician);


    void update(CarFilmTechnician carFilmTechnician);
}
