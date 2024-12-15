package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.lamp.entity.LampService;
import com.yeem.lamp.mapper.LampServiceMapper;
import com.yeem.lamp.service.LampServiceService;
import org.springframework.stereotype.Service;

@Service
public class LampServiceServiceImpl extends ServiceImpl<LampServiceMapper, LampService> implements LampServiceService {

    // 可以在这里添加自定义的业务逻辑方法

}
