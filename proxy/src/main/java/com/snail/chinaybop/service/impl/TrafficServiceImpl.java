package com.snail.chinaybop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.chinaybop.entity.Traffic;
import com.snail.chinaybop.mapper.TrafficMapper;
import com.snail.chinaybop.service.ITrafficService;
import org.springframework.stereotype.Service;

@Service
public class TrafficServiceImpl extends ServiceImpl<TrafficMapper, Traffic> implements ITrafficService {

}
