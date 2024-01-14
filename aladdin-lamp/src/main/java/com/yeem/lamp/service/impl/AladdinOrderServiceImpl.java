package com.yeem.lamp.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.lamp.entity.AladdinOrder;
import com.yeem.lamp.mapper.AladdinOrderMapper;
import com.yeem.lamp.service.IAladdinOrderService;
import org.springframework.stereotype.Service;

@DS("proxy")
@Service
public class AladdinOrderServiceImpl extends ServiceImpl<AladdinOrderMapper, AladdinOrder> implements IAladdinOrderService {

}
