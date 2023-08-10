package com.yeem.car_film_saas.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car_film_saas.entity.SysMail;

import java.util.List;

public interface ISysMailService extends IService<SysMail> {
    List<SysMail> getTodoMail();
}
