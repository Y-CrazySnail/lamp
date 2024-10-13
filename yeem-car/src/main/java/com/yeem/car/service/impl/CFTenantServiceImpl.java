package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.BaseCF;
import com.yeem.car.entity.CFTenant;
import com.yeem.car.mapper.CFTenantMapper;
import com.yeem.car.service.ICFTenantService;
import com.yeem.common.utils.OauthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CFTenantServiceImpl extends ServiceImpl<CFTenantMapper, CFTenant> implements ICFTenantService {

    @Autowired
    private CFTenantMapper tenantMapper;

    public <T extends BaseCF> void auth(LambdaQueryWrapper<T> queryWrapper) {
        String username = OauthUtils.getUsername();
        LambdaQueryWrapper<CFTenant> tenantLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tenantLambdaQueryWrapper.like(CFTenant::getAuthorizedUsername, "|" + username + "|");
        List<CFTenant> tenantList = tenantMapper.selectList(tenantLambdaQueryWrapper);
        if (tenantList.isEmpty()) {
            queryWrapper.eq(BaseCF::getId, null);
        } else {
            queryWrapper.eq(BaseCF::getTenantNo, tenantList.stream().map(CFTenant::getTenantNo).collect(Collectors.toList()));
        }
    }
}
