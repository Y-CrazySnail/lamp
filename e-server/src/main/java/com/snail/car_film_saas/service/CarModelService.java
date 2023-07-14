package com.snail.car_film_saas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.car_film_saas.entity.CarModel;

import java.util.List;

public interface CarModelService extends IService<CarModel> {
    /**
     * id查询所有
     *
     * @return
     */
    List<CarModel> listModelBy();

    /**
     * Id查询所有不被软删除的数据
     * @return
     */
    List<CarModel> listModelByBrandId(Long id);

    /**
     * 分页查询
     * @param current
     * @param size
     * @return
     */
    List<CarModel> listModelByPage(int current ,int size);


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

    /**
     * 增加
     * @param carModel
     */
    void saveCarModel(CarModel carModel);

    /**
     * 修改
     * @param carModel
     */
    void updateCarModel(CarModel carModel);

}
