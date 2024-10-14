package com.yeem.car.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.BaseCF;
import com.yeem.car.entity.CFTenant;

public interface ICFTenantService extends IService<CFTenant> {
    <T extends BaseCF> void auth(LambdaQueryWrapper<T> queryWrapper);
    <T extends BaseCF> void auth(LambdaUpdateWrapper<T> updateWrapper);
    <T extends BaseCF> void auth(T t);
}
