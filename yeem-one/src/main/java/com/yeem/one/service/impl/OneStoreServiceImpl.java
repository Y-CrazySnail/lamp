package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.entity.OneStore;
import com.yeem.one.mapper.OneStoreMapper;
import com.yeem.one.service.IOneStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OneStoreServiceImpl extends ServiceImpl<OneStoreMapper, OneStore> implements IOneStoreService {

    @Autowired
    private OneStoreMapper mapper;

    @Override
    public List<OneStore> listByTenantId(Long tenantId) {
        LambdaQueryWrapper<OneStore> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneStore::getDeleteFlag, false);
        queryWrapper.eq(OneStore::getTenantId, tenantId);
        return mapper.selectList(queryWrapper);
    }
}
