package com.yeem.car_film_saas.service.impl;

import com.yeem.car_film_saas.dto.SysIMSendDTO;
import com.yeem.car_film_saas.dto.SysMailSendDTO;
import com.yeem.car_film_saas.dto.SysSMSSendDTO;
import com.yeem.car_film_saas.entity.SysTemplate;
import com.yeem.car_film_saas.service.ISysIMService;
import com.yeem.car_film_saas.service.ISysMailService;
import com.yeem.car_film_saas.service.ISysTemplateService;
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

    @Override
    public void preSend(SysIMSendDTO sysIMSendDTO) {
        SysTemplate sysTemplate = sysTemplateService.get(sysIMSendDTO.getTemplateType(), sysIMSendDTO.getTemplateName());
        if (SysMailSendDTO.class.equals(sysIMSendDTO.getClass())) {
            sysMailService.save((SysMailSendDTO) sysIMSendDTO, sysTemplate);
        }
        if (SysSMSSendDTO.class.equals(sysIMSendDTO.getClass())) {
            // 调用保存短信接口
        }
    }
}
