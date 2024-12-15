package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.lamp.entity.LampProduct;
import com.yeem.lamp.mapper.LampProductMapper;
import com.yeem.lamp.service.LampProductService;
import org.springframework.stereotype.Service;

@Service
public class LampProductServiceImpl extends ServiceImpl<LampProductMapper, LampProduct> implements LampProductService {

    // 可以在这里添加自定义的业务逻辑方法

}
