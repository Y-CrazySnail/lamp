package com.yeem.car_film_saas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car_film_saas.entity.SysTemplate;

public interface ISysTemplateService extends IService<SysTemplate> {
    SysTemplate get(String type, String name);
}
