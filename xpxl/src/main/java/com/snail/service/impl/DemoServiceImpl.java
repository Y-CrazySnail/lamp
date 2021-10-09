package com.snail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.Demo;
import com.snail.mapper.DemoMapper;
import com.snail.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    public List<Demo> list() {
        QueryWrapper<Demo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return demoMapper.selectList(queryWrapper);
    }
}
