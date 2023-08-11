package com.yeem.car_film_saas.service.impl;

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
import org.springframework.util.StringUtils;

import javax.xml.ws.Action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<SysMail> sysMails = sysMailMapper.selectList(new QueryWrapper<SysMail>().eq("timing_flag", 1));
        // todo 获取待发送邮件列表
        return sysMails;
    }

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
        }
        // 调用邮件service保存
        sysMailMapper.insert(sysMail);
        log.info("发送邮件");
        //  todo
    }
}
