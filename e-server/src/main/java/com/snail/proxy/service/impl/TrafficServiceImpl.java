package com.snail.proxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.proxy.entity.Traffic;
import com.snail.proxy.mapper.TrafficMapper;
import com.snail.proxy.service.ITrafficService;
import org.springframework.stereotype.Service;

@Service
public class TrafficServiceImpl extends ServiceImpl<TrafficMapper, Traffic> implements ITrafficService {

}
