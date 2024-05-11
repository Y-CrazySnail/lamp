package com.yeem.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.im.dto.SysSMSSendDTO;
import com.yeem.im.entity.SysSMS;
import com.yeem.im.entity.SysTemplate;

import java.util.List;

public interface ISysSMSService extends IService<SysSMS> {

     List<SysSMS> getTodo();
     void save(SysSMSSendDTO sysSMSSendDTO, SysTemplate sysTemplate);
     void send();
     void send(SysSMS sms);
}
