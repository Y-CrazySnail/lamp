package com.lamp.common.conreoller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lamp.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController<T extends BaseEntity> {

    @Autowired
    private IService<T> service;

}
