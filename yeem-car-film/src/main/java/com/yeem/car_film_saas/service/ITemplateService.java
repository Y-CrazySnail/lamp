package com.yeem.car_film_saas.service;

import com.yeem.car_film_saas.entity.SysMail;
import com.yeem.car_film_saas.entity.SysTemplate;

import java.util.Map;

public interface ITemplateService {
    public SysTemplate getFinishedEmailTemplate(SysTemplate sysTemplate, Map<String, Object> root);

}
