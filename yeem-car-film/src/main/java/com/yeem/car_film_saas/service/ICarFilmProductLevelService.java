package com.yeem.car_film_saas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car_film_saas.entity.CarFilmProductLevel;

import java.util.List;

public interface ICarFilmProductLevelService extends IService<CarFilmProductLevel> {

    /**
     * 查询所有不被软删除的数据
     *
     * @return
     */
    List<CarFilmProductLevel> list(String productNo,String productLevelName, String status);

    /**
     * 分页查询
     *
     * @return
     */
    IPage<CarFilmProductLevel> pages(int current, int size, String productNo,String productLevelName, String status);

    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    CarFilmProductLevel getById(Long id);

    /**
     * 软删除商品
     *
     * @param
     */
    void remove(CarFilmProductLevel carFilmProductLevel);

    /**
     * 新增商品
     *
     * @param carFilmProductLevel
     */
    boolean save(CarFilmProductLevel carFilmProductLevel);

    /**
     * 更改商品
     *
     * @param carFilmProductLevel
     */
    void update(CarFilmProductLevel carFilmProductLevel);
}
