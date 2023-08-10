package com.yeem.car_film_saas.service;

import com.yeem.car_film_saas.entity.SysMail;
import com.yeem.car_film_saas.entity.SysTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IMailService  {
    public void saveMail(SysMail sysMail, SysTemplate sysTemplate, Map<String, Object> root);

    public ResponseEntity<Object> seedEmail(SysMail sysMail, SysTemplate sysTemplate,Map<String, Object> root);
}
