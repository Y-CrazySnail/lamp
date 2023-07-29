package com.yeem.car_film_saas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car_film_saas.entity.CarFilmMessage;

import java.util.List;

public interface ICarFilmMessageService extends IService<CarFilmMessage> {
    /**
     * 查询全部
     * @return
     */
    List<CarFilmMessage> list(String productNo,String sendStatus,String name);

    /**
     * 分页查询
     * @param current
     * @param size
     * @return
     */
    IPage<CarFilmMessage> pages(int current, int size, String productNo,String sendStatus,String name);

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
