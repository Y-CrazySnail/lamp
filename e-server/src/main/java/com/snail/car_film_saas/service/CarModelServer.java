package com.snail.car_film_saas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.car_film_saas.entity.CarModel;

import java.util.List;

public interface CarModelServer extends IService<CarModel> {
    /**
     * Id查询所有不被软删除的数据
     * @return
     */
    List<CarModel> listModelById(Long id);

    /**
     * id单独查询
     * @param id
     * @return
     */
    CarModel ModelById(Long id);

    /**
     * 删除
     * @param id
     */
    void remove(Long id);
}
