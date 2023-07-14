package com.snail.car_film_saas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.car_film_saas.entity.CarBrand;

import java.util.List;

public interface CarBrandService extends IService<CarBrand> {
    /**
     * 查询所有不被软删除的数据
     * @return
     */
    List<CarBrand> listBrand();

    /**
     * 分页查询
     * @param current
     * @param size
     * @return
     */
    List<CarBrand> listBrandPage(int current,int size);

    List<CarBrand> listLikeBrandPage(int current,int size,String brandName);

    /**
     * id单独查询
     * @param id
     * @return
     */
    CarBrand BrandById(Long id);

    /**
     * 软删除
     * @param carBrand
     */
    void remove(CarBrand carBrand);

    /**
     * 更改
     * @param carBrand
     */
    void updateBrand(CarBrand carBrand);

    /**
     * 新增
     * @param carBrand
     */
    void saveBrand(CarBrand carBrand);
}
