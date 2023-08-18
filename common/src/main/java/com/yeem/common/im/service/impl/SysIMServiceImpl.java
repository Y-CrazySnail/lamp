package com.yeem.common.im.service.impl;

import com.yeem.common.im.dto.SysIMSendDTO;
import com.yeem.common.im.dto.SysMailSendDTO;
import com.yeem.common.im.dto.SysSMSSendDTO;
import com.yeem.common.im.entity.SysTemplate;
import com.yeem.common.im.service.ISysIMService;
import com.yeem.common.im.service.ISysMailService;
import com.yeem.common.im.service.ISysSMSService;
import com.yeem.common.im.service.ISysTemplateService;
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
