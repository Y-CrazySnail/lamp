package com.yeem.car_film_saas.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car_film_saas.dto.SysMailSendDTO;
import com.yeem.car_film_saas.entity.SysMail;
import com.yeem.car_film_saas.entity.SysTemplate;
import com.yeem.car_film_saas.mapper.SysMailMapper;
import com.yeem.car_film_saas.service.ISysMailService;
import com.yeem.common.utils.FreeMakerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLDecoder;
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
            if (StringUtils.isEmpty(sysMail.getAttachment())) {
                MailUtil.send(account, sysMail.getToEmail(), sysMail.getSubject(), sysMail.getContent(), false);
            } else {
//                C:\\Users\\QiMou\\Desktop\\1.jpg
                String[] split = sysMail.getAttachment().split(",");
                System.out.println(split[0]);
                System.out.println(split[1]);
                List<File> fileList = new ArrayList<>();
                for (String str : split) {
                 fileList.add(new File(str));
                }

                MailUtil.send(account, sysMail.getToEmail(), sysMail.getSubject(), sysMail.getContent(), true, FileUtil.file(fileList.toString()));
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
        if (!StringUtils.isEmpty(sysMailSendDTO.getTimingTime())) {
            sysMail.setTimingTime(sysMailSendDTO.getTimingTime());
            sysMail.setTimingFlag(1);
            sysMailMapper.insert(sysMail);
        } else {
            sysMail.setTimingFlag(0);
            sysMailMapper.insert(sysMail);
            this.send(sysMail);
        }
        // 调用邮件service保存
        log.info("发送邮件");
    }
}
