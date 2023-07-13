package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.car_film_saas.entity.CarModel;
import com.snail.car_film_saas.mapper.CarModelMapper;
import com.snail.car_film_saas.service.CarModelServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarModelServerImpl extends ServiceImpl<CarModelMapper, CarModel> implements CarModelServer {
    @Autowired
    private CarModelMapper carModelMapper;

    /**
     * Id查询所有不被软删除的数据
     *
     * @param id
     * @return
     */
    @Override
    public List<CarModel> listModelById(Long id) {
        return carModelMapper.selectList(new QueryWrapper<CarModel>().eq("brand_id", id).eq("delete_flag", 0));
    }

    /**
     * 单独查id
     *
     * @param id
     * @return
     */
    @Override
    public CarModel ModelById(Long id) {
        return carModelMapper.selectById(id);
    }

    /**
     * 软删除
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        UpdateWrapper<CarModel> wrapper = new UpdateWrapper<>();
        wrapper.eq("brand_id", id).set("delete_flag", true);
        carModelMapper.update(null, wrapper);

    }
}
