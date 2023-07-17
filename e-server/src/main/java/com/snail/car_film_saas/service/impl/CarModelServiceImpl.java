package com.snail.car_film_saas.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.snail.car_film_saas.entity.CarModel;
import com.snail.car_film_saas.mapper.CarModelMapper;
import com.snail.car_film_saas.service.ICarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

@Service
public class CarModelServiceImpl extends ServiceImpl<CarModelMapper, CarModel> implements ICarModelService {
    @Autowired
    private CarModelMapper carModelMapper;

    /**
     * 按id查询
     *
     * @return
     */
    @Override
    public List<CarModel> list() {
        return carModelMapper.selectList(new QueryWrapper<CarModel>().eq("delete_flag", 0));
    }

    /**
     * Brand_Id查询所有不被软删除的数据
     *
     * @param id
     * @return
     */
    @Override
    public List<CarModel> listByBrandId(Long id) {
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
    public IPage<CarModel> page(int current, int size, String name) {
       QueryWrapper<CarModel> carModelQueryWrapper=new QueryWrapper<>();
       if (!StringUtils.isEmpty(name)){
           carModelQueryWrapper.like("name",name);
       }
       Page<CarModel> page=new Page<>(current,size);
       return carModelMapper.selectPage(page,carModelQueryWrapper);
    }

    /**
     * 单独查id
     *
     * @param id
     * @return
     */
    @Override
    public CarModel getById(Long id) {
        return carModelMapper.selectById(id);
    }

    /**
     * 软删除
     *
     * @param id
     */
    @Override
    public void removeByBrandId(Long id) {
        UpdateWrapper<CarModel> wrapper = new UpdateWrapper<>();
        wrapper.eq("brand_id", id).set("delete_flag", true);
        carModelMapper.update(null, wrapper);
    }

    /**
     * 新增
     *
     * @param carModel
     */
    @Override
    public boolean save(CarModel carModel) {
        carModel.setNameEn(PinyinUtil.getPinyin(carModel.getNameEn()));
        return SqlHelper.retBool(carModelMapper.insert(carModel));
    }

    /**
     * 更新
     *
     * @param carModel
     */
    @Override
    public void update(CarModel carModel) {
        carModel.setNameEn(PinyinUtil.getPinyin(carModel.getNameEn()));
        carModelMapper.updateById(carModel);
    }

    @Override
    @DS("car-film-saas")
    public boolean updateBatchById(Collection<CarModel> entityList) {
        return super.updateBatchById(entityList);
    }
}
