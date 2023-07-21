package com.yeem.proxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.proxy.entity.Traffic;
import com.yeem.proxy.mapper.TrafficMapper;
import com.yeem.proxy.service.ITrafficService;
import org.springframework.stereotype.Service;

@Service
public class TrafficServiceImpl extends ServiceImpl<TrafficMapper, Traffic> implements ITrafficService {

}
