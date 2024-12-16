package com.lamp.im.service.impl;

import com.lamp.im.dto.SysIMSendDTO;
import com.lamp.im.dto.SysMailSendDTO;
import com.lamp.im.dto.SysSMSSendDTO;
import com.lamp.im.entity.SysTemplate;
import com.lamp.im.service.ISysIMService;
import com.lamp.im.service.ISysMailService;
import com.lamp.im.service.ISysSMSService;
import com.lamp.im.service.ISysTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysIMServiceImpl implements ISysIMService {

    @Autowired
    private ISysTemplateService sysTemplateService;

    @Autowired
    private ISysMailService sysMailService;

    @Autowired
    private ISysSMSService sysSmsService;
    @Override
    public void preSend(SysIMSendDTO sysIMSendDTO) {
        SysTemplate sysTemplate = sysTemplateService.get(sysIMSendDTO.getTemplateType(), sysIMSendDTO.getTemplateName());
        if (SysMailSendDTO.class.equals(sysIMSendDTO.getClass())) {
            sysMailService.save((SysMailSendDTO) sysIMSendDTO, sysTemplate);
        }
        if (SysSMSSendDTO.class.equals(sysIMSendDTO.getClass())) {
            sysSmsService.save((SysSMSSendDTO) sysIMSendDTO ,sysTemplate);
        }
    }
}
