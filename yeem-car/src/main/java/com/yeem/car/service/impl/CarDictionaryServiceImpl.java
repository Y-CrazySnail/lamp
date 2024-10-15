package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.config.Constant;
import com.yeem.car.entity.BaseCarDictionary;
import com.yeem.car.mapper.CarDictionaryMapper;
import com.yeem.car.service.ICarDictionaryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CarDictionaryServiceImpl extends ServiceImpl<CarDictionaryMapper, BaseCarDictionary> implements ICarDictionaryService {

    @Override
    public List<BaseCarDictionary> list(String dictType, String productNo, String tenantNo) {
        LambdaQueryWrapper<BaseCarDictionary> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(BaseCarDictionary::getDeleteFlag, Constant.FALSE_NUMBER);
        if (!Objects.isNull(dictType)) {
            queryWrapper.eq(BaseCarDictionary::getDictType, dictType);
        }
        if (!Objects.isNull(productNo)) {
            queryWrapper.eq(BaseCarDictionary::getProductNo, productNo);
        }
        if (Objects.nonNull(tenantNo)) {
            queryWrapper.eq(BaseCarDictionary::getTenantNo, tenantNo);
        }
        return super.list(queryWrapper);
    }
}
