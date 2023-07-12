package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.car_film_saas.entity.CarFilmProduct;
import com.snail.car_film_saas.mapper.CarFilmProductMapper;
import com.snail.car_film_saas.service.CarFilmProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarFilmProductServerImpl extends ServiceImpl<CarFilmProductMapper, CarFilmProduct> implements CarFilmProductServer{

    @Autowired
    private CarFilmProductMapper carFilmProductMapper;

    /**
     * 查询不被软删除的数据
     * @param
     * @return
     */
    @Override
    public List<CarFilmProduct> listProduct() {
        QueryWrapper<CarFilmProduct> wrapper=new QueryWrapper<>();
        QueryWrapper<CarFilmProduct> deleteFlag = wrapper.eq("delete_flag", 0);
       return carFilmProductMapper.selectList(deleteFlag);
    }

    /**
     * 分页查询所有不被软删除的数据
     * @param current 页数
     * @param size 条数
     * @return
     */
    @Override
    public List<CarFilmProduct> listProductPage(int current, int size) {
        QueryWrapper<CarFilmProduct> wrapper=new QueryWrapper<>();
        QueryWrapper<CarFilmProduct> deleteFlag = wrapper.eq("delete_flag", 0);
        Page<CarFilmProduct> page=new Page<>(current,size);
        return carFilmProductMapper.selectPage(page, deleteFlag).getRecords();
    }

    /**
     * 按id查询 不可以查询到软删除的用户
     * @param id id
     * @return
     */
    @Override
    public CarFilmProduct listProductById(Long id) {
        return carFilmProductMapper.selectById(id);
    }

    /**
     * 软删除
     * @param carFilmProduct
     */
    @Override
    public void removeProduct(CarFilmProduct carFilmProduct) {
        carFilmProduct.setDeleteFlag(true);
        carFilmProductMapper.updateById(carFilmProduct);
    }

    /**
     * 增加商品
     * @param carFilmProduct
     */
    @Override
    public void saveProduct(CarFilmProduct carFilmProduct) {
        carFilmProductMapper.insert(carFilmProduct);
    }

    /**
     * 更改商品
     * @param carFilmProduct
     */
    @Override
    public void updateProduct(CarFilmProduct carFilmProduct) {
        carFilmProductMapper.updateById(carFilmProduct);
    }
}
