package com.yeem.car.film.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.film.entity.BaseCarBrand;

import java.util.List;

public interface ICarBrandService extends IService<BaseCarBrand> {
    /**
     * 查询所有不被软删除的数据
     * @return
     */
    List<BaseCarBrand> list();

    /**
     * 分页查询
     * @param current
     * @param size
     * @return
     */


   IPage<BaseCarBrand> pages(int current, int size, String name);

    /**
     * id单独查询
     * @param id
     * @return
     */
    BaseCarBrand getById(Long id);

    /**
     * 软删除
     * @param baseCarBrand
     */
    void remove(BaseCarBrand baseCarBrand);

    /**
     * 更改
     * @param baseCarBrand
     */
    void update(BaseCarBrand baseCarBrand);

    /**
     * 新增
     * @param baseCarBrand
     */
    boolean save(BaseCarBrand baseCarBrand);
}
