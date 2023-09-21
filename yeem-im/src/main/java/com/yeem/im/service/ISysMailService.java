package com.yeem.im.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.im.dto.SysMailSendDTO;
import com.yeem.im.entity.SysMail;
import com.yeem.im.entity.SysTemplate;

import java.util.List;

public interface ISysMailService extends IService<SysMail> {
    List<SysMail> getTodoMail();

    void save(SysMailSendDTO sysMailSendDTO, SysTemplate sysTemplate);

   void send();

   void send(SysMail sysMail);

}
