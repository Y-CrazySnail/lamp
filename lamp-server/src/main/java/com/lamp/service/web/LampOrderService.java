package com.lamp.service.web;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.entity.LampOrder;
import com.lamp.mapper.LampOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class LampOrderService extends ServiceImpl<LampOrderMapper, LampOrder> {

    // 可以在这里添加自定义的业务逻辑方法

}
