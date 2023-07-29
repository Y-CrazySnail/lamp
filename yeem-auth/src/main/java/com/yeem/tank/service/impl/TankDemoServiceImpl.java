package com.yeem.tank.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.tank.entity.TankDemo;
import com.yeem.tank.mapper.TankDemoMapper;
import com.yeem.tank.service.ITankDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TankDemoServiceImpl extends ServiceImpl<TankDemoMapper, TankDemo> implements ITankDemoService {

    @Autowired
    private TankDemoMapper demoMapper;

    @Override
    public List<TankDemo> list() {
        QueryWrapper<TankDemo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return demoMapper.selectList(queryWrapper);
    }
}
