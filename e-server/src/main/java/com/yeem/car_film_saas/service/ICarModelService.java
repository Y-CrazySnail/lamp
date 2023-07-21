package com.yeem.car_film_saas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car_film_saas.entity.CarModel;

import java.util.List;

public interface ICarModelService extends IService<CarModel> {
    /**
     * id查询所有
     *
     * @return
     */
    List<CarModel> list();

    /**
     * Id查询所有不被软删除的数据
     *
     * @return
     */
    List<CarModel> listByBrandId(Long id);

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @return
     */
    IPage<CarModel> page(int current, int size, String name);


    /**
     * id单独查询
     *
     * @param id
     * @return
     */
    CarModel getById(Long id);

    /**
     * 删除
     *
     * @param id
     */
    void removeByBrandId(Long id);

    /**
     * 增加
     *
     * @param carModel
     */
    boolean save(CarModel carModel);

    /**
     * 修改
     *
     * @param carModel
     */
    void update(CarModel carModel);

}
