package com.yeem.common.im.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.common.im.dto.SysMailSendDTO;
import com.yeem.common.im.entity.SysMail;
import com.yeem.common.im.entity.SysTemplate;

import java.util.List;

public interface ISysMailService extends IService<SysMail> {
    List<SysMail> getTodoMail();

    void save(SysMailSendDTO sysMailSendDTO, SysTemplate sysTemplate);

   void send();

   void send(SysMail sysMail);

}
