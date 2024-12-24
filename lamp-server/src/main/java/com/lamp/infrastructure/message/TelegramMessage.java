package com.lamp.infrastructure.message;

import com.lamp.im.service.ISysTelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelegramMessage {
    @Autowired
    private ISysTelegramService sysTelegramService;

}
