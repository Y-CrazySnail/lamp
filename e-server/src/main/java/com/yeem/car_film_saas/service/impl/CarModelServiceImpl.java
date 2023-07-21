package com.yeem.car_film_saas.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car_film_saas.entity.CarModel;
import com.yeem.car_film_saas.mapper.CarModelMapper;
import com.yeem.car_film_saas.service.ICarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

@Service
public class CarModelServiceImpl extends ServiceImpl<CarModelMapper, CarModel> implements ICarModelService {
    @Autowired
    private CarModelMapper carModelMapper;
    @Autowired
    private CarLevelServiceImpl carLevelService;

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public List<CarModel> list() {
        List<CarModel> carModels = carModelMapper.selectList(new QueryWrapper<CarModel>().eq("delete_flag", 0));
       //用carLevelService里的查询CarLevel方法 得到levelName 加入每个CarModel里
        for (CarModel carModel : carModels) {
            carModel.setLevelName(carLevelService.queryCarLevel(carModel.getLevelNo()).getLevelName());
        }
        return carModels;
    }

    /**
     * Brand_Id查询所有不被软删除的数据
     *
     * @param id
     * @return
     */
    @Override
    public List<CarModel> listByBrandId(Long id) {
        List<CarModel> carModels = carModelMapper.selectList(new QueryWrapper<CarModel>().eq("brand_id", id).eq("delete_flag", 0));
        for (CarModel carModel : carModels) {
            carModel.setLevelName(carLevelService.queryCarLevel(carModel.getLevelNo()).getLevelName());
        }
        return carModels;
    }

    /**
     * 分页模糊查询
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
       carModelQueryWrapper.eq("delete_flag", 0);
       Page<CarModel> page=new Page<>(current,size);
        Page<CarModel> carModelPage = carModelMapper.selectPage(page, carModelQueryWrapper);
        List<CarModel> records = carModelPage.getRecords();
        for (CarModel carModel : records) {
            carModel.setLevelName(carLevelService.queryCarLevel(carModel.getLevelNo()).getLevelName());
        }
        return carModelPage;
    }

    /**
     * 单独查id
     *
     * @param id
     * @return
     */
    @Override
    public CarModel getById(Long id) {
        CarModel carModel = carModelMapper.selectById(id);
        carModel.setLevelName(carLevelService.queryCarLevel(carModel.getLevelNo()).getLevelName());
        return carModel;
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
