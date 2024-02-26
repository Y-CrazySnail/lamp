package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneTenant;

import java.util.Set;

public interface IOneTenantService extends IService<OneTenant> {
    boolean authenticate(Long tenantId);

    Set<Long> authorizedTenantIdSet();
}
