package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneTenant;

public interface IOneTenantService extends IService<OneTenant> {
    boolean authenticate(Long tenantId);
}
