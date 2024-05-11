package com.yeem.im.service.impl;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.im.dto.SysMailSendDTO;
import com.yeem.im.entity.SysMail;
import com.yeem.im.entity.SysTemplate;
import com.yeem.im.mapper.SysMailMapper;
import com.yeem.im.service.ISysMailService;
import com.yeem.common.utils.FreeMakerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class SysMailServiceImpl extends ServiceImpl<SysMailMapper, SysMail> implements ISysMailService {

    @Autowired
    private SysMailMapper sysMailMapper;

    @Autowired
    private Environment environment;

    /**
     * 获取待发送邮件列表
     *
     * @return 待发送邮件列表
     */
    @Override
    public List<SysMail> getTodoMail() {
        return sysMailMapper.getTodoMail();
    }

    /**
     * 定时发送
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void send() {
        List<SysMail> todoMail = this.getTodoMail();
        for (SysMail sysMail : todoMail) {
            this.send(sysMail);
        }
    }

    /**
     * 立刻发送
     *
     * @param sysMail
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void send(SysMail sysMail) {
        MailAccount account = new MailAccount();
        account.setHost(environment.getProperty("mail.host"));
        account.setPort(Integer.valueOf(Objects.requireNonNull(environment.getProperty("mail.port"))));
        account.setAuth(true);
        account.setFrom(environment.getProperty("mail.from"));
        account.setUser(environment.getProperty("mail.user"));
        account.setPass(environment.getProperty("mail.pass"));
        try {
            if (StrUtil.isEmpty(sysMail.getAttachment())) {
                MailUtil.send(account, sysMail.getToEmail(), sysMail.getSubject(), sysMail.getContent(), sysMail.isHtmlFlag());
            } else {
                List<File> attachmentFileList = new ArrayList<>();
                for (String attachment : sysMail.getAttachment().split(",")) {
                    File file = new File(attachment);
                    if (file.exists() && file.isFile()) {
                        attachmentFileList.add(new File(attachment));
                    }
                }
                MailUtil.send(account, sysMail.getToEmail(), sysMail.getSubject(), sysMail.getContent(), sysMail.isHtmlFlag(), attachmentFileList.toArray(new File[0]));
            }
            sysMail.setState(1);
            sysMail.setSendTime(new Date());
        } catch (Exception e) {
            sysMail.setState(9);
            sysMail.setTryTime(sysMail.getTryTime() + 1);
            sysMail.setException(e.toString());
            log.error("send mail error", e);
        } finally {
            sysMailMapper.updateById(sysMail);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void save(SysMailSendDTO sysMailSendDTO, SysTemplate sysTemplate) {
        // 组装SysMail
        SysMail sysMail = new SysMail();
        sysMail.setContent(FreeMakerUtils.getContent(sysTemplate.getContent(), sysMailSendDTO.getReplaceMap()));
        sysMail.setSubject(FreeMakerUtils.getContent(sysTemplate.getSubject(), sysMailSendDTO.getReplaceMap()));
        sysMail.setFromEmail(environment.getProperty("mail.from"));
        sysMail.setToEmail(sysMailSendDTO.getToEmail());
        sysMail.setAttachment(sysMailSendDTO.getAttachment());
        sysMail.setBusinessId(sysMailSendDTO.getBusinessId());
        sysMail.setBusinessName(sysTemplate.getName());
        sysMail.setHtmlFlag(sysTemplate.isMailHtmlFlag());
        if (null != sysMailSendDTO.getTimingTime()) {
            sysMail.setTimingFlag(1);
            sysMail.setTimingTime(sysMailSendDTO.getTimingTime());
            sysMailMapper.insert(sysMail);
        } else {
            sysMail.setTimingFlag(0);
            sysMailMapper.insert(sysMail);
            this.send(sysMail);
        }
    }
}
