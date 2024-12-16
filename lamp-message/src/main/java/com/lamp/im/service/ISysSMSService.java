package com.lamp.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lamp.im.dto.SysSMSSendDTO;
import com.lamp.im.entity.SysSMS;
import com.lamp.im.entity.SysTemplate;

import java.util.List;

public interface ISysSMSService extends IService<SysSMS> {

     List<SysSMS> getTodo();
     void save(SysSMSSendDTO sysSMSSendDTO, SysTemplate sysTemplate);
     void send();
     void send(SysSMS sms);
}
