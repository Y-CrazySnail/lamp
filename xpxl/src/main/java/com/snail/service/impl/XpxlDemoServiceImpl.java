package com.snail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.XpxlDemo;
import com.snail.mapper.XpxlDemoMapper;
import com.snail.service.IXpxlDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XpxlDemoServiceImpl extends ServiceImpl<XpxlDemoMapper, XpxlDemo> implements IXpxlDemoService {

    @Autowired
    private XpxlDemoMapper demoMapper;

    @Override
    public List<XpxlDemo> list() {
        QueryWrapper<XpxlDemo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return demoMapper.selectList(queryWrapper);
    }
}
