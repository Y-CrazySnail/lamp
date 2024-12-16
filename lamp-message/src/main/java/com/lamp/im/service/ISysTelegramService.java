package com.lamp.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lamp.im.dto.SysTelegramSendDTO;
import com.lamp.im.entity.SysTelegram;

public interface ISysTelegramService extends IService<SysTelegram> {
    void send(SysTelegramSendDTO sysTelegramSendDTO);
}
