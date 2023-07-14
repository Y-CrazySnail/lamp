package com.snail.car_film_saas.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.car_film_saas.entity.CarModel;
import com.snail.car_film_saas.mapper.CarModelMapper;
import com.snail.car_film_saas.service.CarModelServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CarModelServerImpl extends ServiceImpl<CarModelMapper, CarModel> implements CarModelServer {
    @Autowired
    private CarModelMapper carModelMapper;

    /**
     * 按id查询
     *
     * @return
     */
    @Override
    public List<CarModel> listModelBy() {
        return carModelMapper.selectList(new QueryWrapper<CarModel>().eq("delete_flag", 0));
    }

    /**
     * Brand_Id查询所有不被软删除的数据
     *
     * @param id
     * @return
     */
    @Override
    public List<CarModel> listModelByBrandId(Long id) {
        return carModelMapper.selectList(new QueryWrapper<CarModel>().eq("brand_id", id).eq("delete_flag", 0));
    }

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public List<CarModel> listModelByPage(int current, int size) {
        return carModelMapper.selectPage(new Page<>(current, size), new QueryWrapper<CarModel>().eq("delete_flag", 0)).getRecords();
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
     * @param id
     */
    @Override
    public void remove(Long id) {
        UpdateWrapper<CarModel> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("delete_flag", true);
        carModelMapper.update(null, wrapper);
    }

    /**
     * 新增
     * @param carModel
     */
    @Override
    public void saveCarModel(CarModel carModel) {
     carModel.setnameEn(PinyinUtil.getPinyin(carModel.getnameEn()));
        carModelMapper.insert(carModel);
    }

    /**
     * 更新
     * @param carModel
     */
    @Override
    public void updateCarModel(CarModel carModel) {
        carModel.setnameEn(PinyinUtil.getPinyin(carModel.getnameEn()));
        carModelMapper.updateById(carModel);
    }

    @Override
    @DS("car-film-saas")
    public boolean updateBatchById(Collection<CarModel> entityList) {
        return super.updateBatchById(entityList);
    }
}
