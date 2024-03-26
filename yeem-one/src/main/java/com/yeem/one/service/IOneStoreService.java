package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneStore;

import java.util.List;

public interface IOneStoreService extends IService<OneStore> {
    List<OneStore> listByTenantId(Long tenantId);

    OneStore getDefault(Long tenantId);
}
