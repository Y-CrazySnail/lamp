package com.yeem.car_film_saas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car_film_saas.dto.SysMailSendDTO;
import com.yeem.car_film_saas.entity.SysMail;
import com.yeem.car_film_saas.entity.SysTemplate;
import com.yeem.car_film_saas.mapper.SysMailMapper;
import com.yeem.car_film_saas.service.ISysMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysMailServiceImpl extends ServiceImpl<SysMailMapper, SysMail> implements ISysMailService {

    /**
     * 获取待发送邮件列表
     * @return 待发送邮件列表
     */
    @Override
    public List<SysMail> getTodoMail() {
        // todo 获取待发送邮件列表
        return null;
    }

    @Override
    public void save(SysMailSendDTO sysMailSendDTO, SysTemplate sysTemplate) {
        log.info("发送邮件");
        // todo 组装SysMail
        // todo 调用邮件service保存
    }
}
