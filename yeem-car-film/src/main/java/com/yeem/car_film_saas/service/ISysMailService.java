package com.yeem.car_film_saas.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car_film_saas.dto.SysMailSendDTO;
import com.yeem.car_film_saas.entity.SysMail;
import com.yeem.car_film_saas.entity.SysTemplate;

import java.util.List;

public interface ISysMailService extends IService<SysMail> {
    List<SysMail> getTodoMail();

    void save(SysMailSendDTO sysMailSendDTO, SysTemplate sysTemplate);
}
