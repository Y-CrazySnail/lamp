package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.BCDictionary;
import com.yeem.car.entity.BCModel;
import com.yeem.car.mapper.BCModelMapper;
import com.yeem.car.service.IBCDictionaryService;
import com.yeem.car.service.IBCModelService;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yeem.car.entity.BCDictionary.DICT_CAR_LEVEL;

@Service
public class BCModelServiceImpl extends ServiceImpl<BCModelMapper, BCModel> implements IBCModelService {

    @Autowired
    private IBCDictionaryService dictionaryService;

    @Autowired
    private BCModelMapper modelMapper;

    @Override
    public List<BCModel> listForWechat(Long brandId) {
        LambdaQueryWrapper<BCModel> queryWrapper = Wrappers.lambdaQuery(BCModel.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(BCModel::getBrandId, brandId);
        queryWrapper.orderByAsc(BCModel::getNameEn);
        List<BCDictionary> carLevelList = dictionaryService.list(DICT_CAR_LEVEL);
        return modelMapper.selectList(queryWrapper);
    }
}
