package com.yeem.lamp.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinServer;
import com.yeem.lamp.mapper.AladdinServerMapper;
import com.yeem.lamp.service.IAladdinServerService;
import org.springframework.stereotype.Service;

import java.util.List;

@DS("proxy")
@Service
public class AladdinServerServiceImpl extends ServiceImpl<AladdinServerMapper, AladdinServer> implements IAladdinServerService {
    @Override
    public List<AladdinServer> list() {
        QueryWrapper<AladdinServer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        return super.list(queryWrapper);
    }
}
