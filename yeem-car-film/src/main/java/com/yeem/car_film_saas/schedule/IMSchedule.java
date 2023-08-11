package com.yeem.car_film_saas.schedule;

import cn.hutool.extra.mail.MailUtil;
import com.yeem.car_film_saas.entity.SysMail;
import com.yeem.car_film_saas.service.ISysIMService;
import com.yeem.car_film_saas.service.ISysMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class IMSchedule {

    @Autowired
    private ISysIMService sysIMService;

    @Autowired
    ISysMailService sysMailService;
    /**
     * 发送邮件定时任务 每10秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void schedule() {
        log.info("send mail start:{}", LocalDateTime.now());
        List<SysMail> todoMail = sysMailService.getTodoMail();
        for (SysMail sysMail : todoMail) {
            MailUtil.send(sysMail.getToEmail(),sysMail.getSubject(),sysMail.getContent(), sysMail.getAttachment().isEmpty());
        }

        //  todo 定时任务 定时扫描
    }
}
