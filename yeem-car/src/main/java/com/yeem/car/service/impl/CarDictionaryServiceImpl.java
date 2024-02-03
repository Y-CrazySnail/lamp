package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.config.Constant;
import com.yeem.car.entity.BaseCarDictionary;
import com.yeem.car.mapper.CarDictionaryMapper;
import com.yeem.car.service.ICarDictionaryService;
import com.yeem.common.entity.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarDictionaryServiceImpl extends ServiceImpl<CarDictionaryMapper, BaseCarDictionary> implements ICarDictionaryService {

    @Override
    public List<BaseCarDictionary> list(String productNo) {
        QueryWrapper<BaseCarDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("product_no", productNo);
        return super.list(queryWrapper);
    }
}
