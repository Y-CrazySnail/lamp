package com.lamp.service.web;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.entity.LampProduct;
import com.lamp.mapper.LampProductMapper;
import org.springframework.stereotype.Service;

@Service
public class LampProductService extends ServiceImpl<LampProductMapper, LampProduct> {

    // 可以在这里添加自定义的业务逻辑方法

}
