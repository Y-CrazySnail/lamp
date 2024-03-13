package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneAddress;
import com.yeem.one.mapper.OneAddressMapper;
import com.yeem.one.service.IOneAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OneAddressServiceImpl extends ServiceImpl<OneAddressMapper, OneAddress> implements IOneAddressService {

    @Autowired
    private OneAddressMapper mapper;

    @Override
    public List<OneAddress> listByUserId(Long userId) {
        LambdaQueryWrapper<OneAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneAddress::getDeleteFlag, Constant.BOOLEAN_FALSE);
        queryWrapper.eq(OneAddress::getUserId, userId);
        return mapper.selectList(queryWrapper);
    }
}
