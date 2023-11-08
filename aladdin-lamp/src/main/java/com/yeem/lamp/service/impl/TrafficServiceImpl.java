package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.lamp.entity.Traffic;
import com.yeem.lamp.mapper.TrafficMapper;
import com.yeem.lamp.service.ITrafficService;
import org.springframework.stereotype.Service;

@Service
public class TrafficServiceImpl extends ServiceImpl<TrafficMapper, Traffic> implements ITrafficService {

}
