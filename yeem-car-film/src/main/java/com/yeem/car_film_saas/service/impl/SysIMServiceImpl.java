package com.yeem.car_film_saas.service.impl;

import com.yeem.car_film_saas.dto.SysIMSendDTO;
import com.yeem.car_film_saas.dto.SysMailSendDTO;
import com.yeem.car_film_saas.dto.SysSMSSendDTO;
import com.yeem.car_film_saas.service.ISysIMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysIMServiceImpl implements ISysIMService {

    @Override
    public void preSend(SysIMSendDTO sysIMSendDTO) {
        if (SysMailSendDTO.class.equals(sysIMSendDTO.getClass())) {
            send((SysMailSendDTO) sysIMSendDTO);
        }
        if (SysSMSSendDTO.class.equals(sysIMSendDTO.getClass())) {
            send((SysSMSSendDTO) sysIMSendDTO);
        }
    }

    private void send(SysMailSendDTO sysMailSendDTO) {
        log.info("发送邮件");
        // todo 组装SysMail
        // todo 调用邮件service保存
    }

    private void send(SysSMSSendDTO sysSMSSendDTO) {
        log.info("发送短信");
    }
}
