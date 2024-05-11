package com.yeem.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.im.dto.SysTelegramSendDTO;
import com.yeem.im.entity.SysTelegram;

public interface ISysTelegramService extends IService<SysTelegram> {
    void send(SysTelegramSendDTO sysTelegramSendDTO);
}
