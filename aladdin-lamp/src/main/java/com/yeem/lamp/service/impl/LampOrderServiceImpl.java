package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.lamp.entity.LampOrder;
import com.yeem.lamp.mapper.LampOrderMapper;
import com.yeem.lamp.service.LampOrderService;
import org.springframework.stereotype.Service;

@Service
public class LampOrderServiceImpl extends ServiceImpl<LampOrderMapper, LampOrder> implements LampOrderService {

    // 可以在这里添加自定义的业务逻辑方法

}
