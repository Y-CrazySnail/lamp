package com.lamp.controller.web;

import com.lamp.im.dto.SysMailSendDTO;
import com.lamp.im.service.ISysIMService;
import com.lamp.service.web.LampSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/subscribe")
public class LampSubscriptionController {

    @Autowired
    private LampSubscriptionService subscriptionService;

    @Autowired
    private ISysIMService sysIMService;

    @GetMapping("test")
    public String test() {
        SysMailSendDTO sysMailSendDTO = new SysMailSendDTO();
        sysMailSendDTO.setAttachment("C:\\A.sql");
        sysMailSendDTO.setTemplateName("test");
        sysMailSendDTO.setTemplateType("mail");
        sysMailSendDTO.setBusinessId(0);
        Map<String, Object> replaceMap = new HashMap<>();
        replaceMap.put("date", new Date().toString());
        sysMailSendDTO.setReplaceMap(replaceMap);
        sysMailSendDTO.setToEmail("haisong0230@gmail.com");
        sysIMService.preSend(sysMailSendDTO);
        return new Date().toString();
    }

    @GetMapping("/clash/{uuid}")
    public String clash(@PathVariable("uuid") String uuid) {
        return subscriptionService.clash(uuid);
    }

    @GetMapping("/shadowrocket/{uuid}")
    public String shadowrocket(@PathVariable("uuid") String uuid) {
        return subscriptionService.v2ray(uuid);
    }

}
