package com.yeem.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.im.entity.SysTemplate;

public interface ISysTemplateService extends IService<SysTemplate> {
    SysTemplate get(String type, String name);
}
