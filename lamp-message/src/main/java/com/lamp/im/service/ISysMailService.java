package com.lamp.im.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lamp.im.dto.SysMailSendDTO;
import com.lamp.im.entity.SysMail;
import com.lamp.im.entity.SysTemplate;

import java.util.List;

public interface ISysMailService extends IService<SysMail> {
    List<SysMail> getTodoMail();

    void save(SysMailSendDTO sysMailSendDTO, SysTemplate sysTemplate);

    void send();

    void send(SysMail sysMail);

}
