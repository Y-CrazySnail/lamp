package com.yeem.car_film_saas.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car_film_saas.dto.SysMailSendDTO;
import com.yeem.car_film_saas.entity.SysMail;
import com.yeem.car_film_saas.entity.SysTemplate;
import com.yeem.car_film_saas.mapper.SysMailMapper;
import com.yeem.car_film_saas.service.ISysMailService;
import com.yeem.common.utils.FreeMakerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.xml.ws.Action;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.druid.sql.visitor.SQLEvalVisitorUtils.eq;

@Slf4j
@Service
public class SysMailServiceImpl extends ServiceImpl<SysMailMapper, SysMail> implements ISysMailService {

    @Autowired
    private SysMailMapper sysMailMapper;

    /**
     * 获取待发送邮件列表
     *
     * @return 待发送邮件列表
     */
    @Override
    public List<SysMail> getTodoMail() {
        return sysMailMapper.getTodoMail();
    }

    @Override
    public void send() {
        List<SysMail> todoMail = this.getTodoMail();
        for (SysMail sysMail : todoMail) {
            this.send(sysMail);
        }
    }

    @Override
    public void send(SysMail sysMail) {
        if (StringUtils.isEmpty(sysMail.getAttachment())) {
            MailUtil.send(sysMail.getToEmail(), sysMail.getSubject(), sysMail.getContent(), false);
        } else {
            MailUtil.send(sysMail.getToEmail(), sysMail.getSubject(), sysMail.getContent(), true, FileUtil.file("d:/aaa.xml"));
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void save(SysMailSendDTO sysMailSendDTO, SysTemplate sysTemplate) {
        // 组装SysMail
        SysMail sysMail = new SysMail();
        sysMail.setContent(FreeMakerUtils.getContent(sysTemplate.getContent(), sysMailSendDTO.getReplaceMap()));
        sysMail.setSubject(FreeMakerUtils.getContent(sysTemplate.getSubject(), sysMailSendDTO.getReplaceMap()));
        sysMail.setToEmail(sysMailSendDTO.getToEmail());
        sysMail.setAttachment(sysMailSendDTO.getAttachment());
        if (!StringUtils.isEmpty(sysMailSendDTO.getTimingTime())) {
            sysMail.setTimingTime(sysMailSendDTO.getTimingTime());
            sysMail.setTimingFlag(1);
        } else {
            sysMail.setTimingFlag(0);
            try {
                this.send(sysMail);
                sysMail.setState(1);
            } catch (Exception e) {
                sysMail.setException("异常为" + e);
            }
        }
        // 调用邮件service保存
        sysMailMapper.insert(sysMail);
        log.info("发送邮件");
    }
}
