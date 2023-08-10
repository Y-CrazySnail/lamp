package com.yeem.car_film_saas.service.impl;

import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.car_film_saas.entity.SysMail;
import com.yeem.car_film_saas.entity.SysTemplate;
import com.yeem.car_film_saas.mapper.MailMapper;
import com.yeem.car_film_saas.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class MailServiceImpl implements IMailService {
    @Autowired
    private TemplateServiceImpl templateServiceImpl;

    @Autowired
    private MailMapper mailMapper;

    @Override
    public void saveMail(SysMail sysMail, SysTemplate sysTemplate, Map<String, Object> root) {
        SysTemplate mail = templateServiceImpl.getFinishedEmailTemplate(sysTemplate,root);
        sysMail.setContent(mail.getContent());
        sysMail.setSubject(mail.getSubject());
        mailMapper.insert(sysMail);
    }



    @Override
//    @Scheduled(cron = "* 0/5 * * * ?")
    public ResponseEntity<Object> seedEmail(SysMail sysMail, SysTemplate sysTemplate,Map<String, Object> root) {
        saveMail(sysMail, sysTemplate,root);

        return null;
    }
}
