package com.yeem.car_film_saas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car_film_saas.entity.CarBrand;

import java.util.List;

public interface ICarBrandService extends IService<CarBrand> {
    /**
     * 查询所有不被软删除的数据
     * @return
     */
    List<CarBrand> list();

    /**
     * 分页查询
     * @param current
     * @param size
     * @return
     */


   IPage<CarBrand> page(int current, int size, String brandName);

    /**
     * id单独查询
     * @param id
     * @return
     */
    CarBrand getById(Long id);

    /**
     * 软删除
     * @param carBrand
     */
    void remove(CarBrand carBrand);

    /**
     * 更改
     * @param carBrand
     */
    void update(CarBrand carBrand);

    /**
     * 新增
     * @param carBrand
     */
    boolean save(CarBrand carBrand);
}
