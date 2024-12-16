package com.lamp.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.lamp.im.dto.SysTelegramSendDTO;
import com.lamp.im.entity.SysTelegram;
import com.lamp.im.entity.SysTemplate;
import com.lamp.im.mapper.SysTelegramMapper;
import com.lamp.im.service.ISysTelegramService;
import com.lamp.im.service.ISysTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class SysTelegramServiceImpl extends ServiceImpl<SysTelegramMapper, SysTelegram> implements ISysTelegramService {
    @Value("${telegram.token:telegram_token}")
    private String token;
    @Value("${telegram.chat-id:telegram_chat_id}")
    private String chatId;

    @Autowired
    private ISysTemplateService sysTemplateService;

    @Override
    public void send(SysTelegramSendDTO sysTelegramSendDTO) {
        SysTemplate sysTemplate = sysTemplateService.get(sysTelegramSendDTO.getTemplateType(), sysTelegramSendDTO.getTemplateName());
        String messageText = sysTemplate.getContent();
        for (Map.Entry<String, Object> entry : sysTelegramSendDTO.getReplaceMap().entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            messageText = messageText.replace("#{" + key + "}", value);
        }

        String url = "https://api.telegram.org/bot" + token + "/sendMessage";

        String params = "chat_id=" + chatId + "&text=" + messageText;

        HttpResponse response = HttpRequest.post(url)
                .body(params)
                .execute();

        // 输出响应内容
        log.info(response.body());
    }
}
