package com.yeem.common.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.common.im.dto.SysMailSendDTO;
import com.yeem.common.im.dto.SysSMSSendDTO;
import com.yeem.common.im.entity.SysSms;
import com.yeem.common.im.entity.SysTemplate;

import java.util.List;

public interface ISysSmsService extends IService<SysSms> {

     List<SysSms> getTodo();
     public void save(SysSMSSendDTO sysSMSSendDTO, SysTemplate sysTemplate);
     public void send();
     void sendSms(SysSms sms);
}
