package com.yeem.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.im.entity.SysTelegram;
import com.yeem.im.mapper.SysTelegramMapper;
import com.yeem.im.service.ISysTelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysTelegramServiceImpl extends ServiceImpl<SysTelegramMapper, SysTelegram> implements ISysTelegramService {
    @Value("${tencent.secret-id}")
    private String TENCENT_SECRET_ID;
}
