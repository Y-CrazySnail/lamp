package com.yeem.car.film.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.film.entity.CarFilmProduct;

import java.util.List;

public interface ICarFilmProductService extends IService<CarFilmProduct> {

    /**
     * 查询所有不被软删除的数据
     *
     * @return
     */
    List<CarFilmProduct> list(String productNo, String productLevelName, String status);

    /**
     * 分页查询
     *
     * @return
     */
    IPage<CarFilmProduct> pages(int current, int size, String productNo, String productLevelName, String status);

    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    CarFilmProduct getById(Long id);

    /**
     * 软删除商品
     *
     * @param
     */
    void remove(CarFilmProduct carFilmProductLevel);

    /**
     * 新增商品
     *
     * @param carFilmProductLevel
     */
    boolean save(CarFilmProduct carFilmProductLevel);

    /**
     * 更改商品
     *
     * @param carFilmProductLevel
     */
    void update(CarFilmProduct carFilmProductLevel);
}
