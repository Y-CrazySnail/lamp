package com.snail.car_film_saas.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.car_film_saas.entity.CarFilmMessage;

import java.util.List;

public interface ICarFilmMessageService extends IService<CarFilmMessage> {
    /**
     * 查询全部
     *
     * @param carFilmMessage
     * @return
     */
    List<CarFilmMessage> list(CarFilmMessage carFilmMessage);

    /**
     * 分页查询
     * @param current
     * @param size
     * @param carFilmMessage
     * @return
     */
    IPage<CarFilmMessage> pages(int current, int size, CarFilmMessage carFilmMessage);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CarFilmMessage getById(Long id);


    /**
     * 软删除
     * @param carFilmMessage
     * @return
     */
     void remove(CarFilmMessage carFilmMessage);

    /**
     * 新增
     * @param carFilmMessage
     * @return
     */
     boolean save(CarFilmMessage carFilmMessage);

    /**
     * 修改
     * @param carFilmMessage
     */
     void update(CarFilmMessage carFilmMessage);
}
