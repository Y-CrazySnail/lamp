package com.lamp.infrastructure.message;

import com.lamp.domain.entity.Member;
import com.lamp.domain.entity.Server;
import com.lamp.im.dto.SysTelegramSendDTO;
import com.lamp.im.service.ISysTelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TelegramMessage {
    @Autowired
    private ISysTelegramService sysTelegramService;

    /**
     * 发送会员登录提醒
     * @param member 会员
     */
    public void sendLoginNotice(Member member) {
        try {
            SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
            sysTelegramSendDTO.setTemplateName("login");
            sysTelegramSendDTO.setTemplateType("telegram");
            Map<String, Object> replaceMap = new HashMap<>();
            replaceMap.put("email", member.getEmail());
            sysTelegramSendDTO.setReplaceMap(replaceMap);
            sysTelegramService.send(sysTelegramSendDTO);
        } catch (Exception e) {
            log.error("send telegram message error:", e);
        }
    }

    /**
     * 发送服务过期提醒
     * @param member 会员
     */
    public void sendExpireNotice(Member member, Server server) {
        try {
            SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
            sysTelegramSendDTO.setTemplateName("service_expire");
            sysTelegramSendDTO.setTemplateType("telegram");
            Map<String, Object> replaceMap = new HashMap<>();
            replaceMap.put("wechat", member.getWechat());
            replaceMap.put("email", member.getEmail());
            replaceMap.put("server", server.getId() + server.getRegion());
            sysTelegramSendDTO.setReplaceMap(replaceMap);
            sysTelegramService.send(sysTelegramSendDTO);
        } catch (Exception e) {
            log.error("send telegram message error:", e);
        }
    }

    /**
     * 发送服务超额提醒
     * @param member 会员
     */
    public void sendExcessNotice(Member member, Server server) {
        try {
            SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
            sysTelegramSendDTO.setTemplateName("service_excess");
            sysTelegramSendDTO.setTemplateType("telegram");
            Map<String, Object> replaceMap = new HashMap<>();
            replaceMap.put("wechat", member.getWechat());
            replaceMap.put("email", member.getEmail());
            replaceMap.put("server", server.getId() + server.getRegion());
            sysTelegramSendDTO.setReplaceMap(replaceMap);
            sysTelegramService.send(sysTelegramSendDTO);
        } catch (Exception e) {
            log.error("send telegram message error:", e);
        }
    }

    /**
     * 发送服务恢复提醒
     * @param member 会员
     */
    public void sendRecoverNotice(Member member, Server server) {
        try {
            SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
            sysTelegramSendDTO.setTemplateName("service_recover");
            sysTelegramSendDTO.setTemplateType("telegram");
            Map<String, Object> replaceMap = new HashMap<>();
            replaceMap.put("wechat", member.getWechat());
            replaceMap.put("email", member.getEmail());
            replaceMap.put("server", server.getId() + server.getRegion());
            sysTelegramSendDTO.setReplaceMap(replaceMap);
            sysTelegramService.send(sysTelegramSendDTO);
        } catch (Exception e) {
            log.error("send telegram message error:", e);
        }
    }
}
