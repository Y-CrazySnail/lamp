package com.yeem.car_film_saas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car_film_saas.entity.SysMail;
import com.yeem.car_film_saas.mapper.SysMailMapper;
import com.yeem.car_film_saas.service.ISysMailService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
