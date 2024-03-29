package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.entity.OneSku;
import com.yeem.one.mapper.OneSkuMapper;
import com.yeem.one.service.IOneSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OneSkuServiceImpl extends ServiceImpl<OneSkuMapper, OneSku> implements IOneSkuService {

    @Autowired
    private OneSkuMapper mapper;

    @Override
    public List<OneSku> listBySpuId(Long spuId) {
        LambdaQueryWrapper<OneSku> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneSku::getDeleteFlag, false);
        queryWrapper.eq(OneSku::getSpuId, spuId);
        queryWrapper.eq(OneSku::getSkuStatus, true);
        return mapper.selectList(queryWrapper);
    }
}
