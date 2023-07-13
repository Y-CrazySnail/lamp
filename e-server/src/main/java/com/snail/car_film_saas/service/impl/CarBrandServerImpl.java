package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.car_film_saas.entity.CarBrand;
import com.snail.car_film_saas.entity.CarModel;
import com.snail.car_film_saas.mapper.CarBrandMapper;
import com.snail.car_film_saas.service.CarBrandServer;
import com.snail.car_film_saas.service.CarModelServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarBrandServerImpl extends ServiceImpl<CarBrandMapper, CarBrand> implements CarBrandServer {
    @Autowired
    private CarBrandMapper carBrandMapper;
    @Autowired
    private CarModelServer carModelService;


    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public List<CarBrand> listBrand() {
//        拿到Brand表中没被软删除的
        List<CarBrand> carBrandList = carBrandMapper.selectList(new QueryWrapper<CarBrand>().eq("delete_flag", 0));
//        便利出来
        for (CarBrand carBrand : carBrandList) {
//            找到对应的数据 放进carBrand中
            carBrand.setListcarModel(carModelService.listModelById(carBrand.getId()));
        }
        return carBrandList;
    }

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public List<CarBrand> listBrandPage(int current, int size) {
        Page<CarBrand> page = new Page<>(current, size);
        List<CarBrand> records = carBrandMapper.selectPage(page, new QueryWrapper<CarBrand>().eq("delete_flag", 0)).getRecords();
        for (CarBrand record : records) {
            record.setListcarModel(carModelService.listModelById(record.getId()));
        }
        return records;
    }

    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    @Override
    public CarBrand BrandById(Long id) {
        List<CarModel> carModels = carModelService.listModelById(id);
        CarBrand carBrand = carBrandMapper.selectById(id);
        carBrand.setListcarModel(carModels);
        return carBrand;
    }

    /**
     * 软删除
     * @param carBrand
     */
    @Override
    public void remove(CarBrand carBrand) {

        carModelService.remove(carBrand.getId());
     carBrand.setDeleteFlag(true);
     carBrandMapper.updateById(carBrand);

    }

    /**
     * 更改
     * @param carBrand
     */
    @Override
    public void updateBrand(CarBrand carBrand) {
        carBrandMapper.updateById(carBrand);
        carModelService.updateBatchById(carBrand.getListcarModel());

    }

    /**
     * 增加
     */
    @Override
    public void saveBrand(CarBrand carBrand) {
        carModelService.saveBatch(carBrand.getListcarModel());
        carBrandMapper.insert(carBrand);
    }
}
