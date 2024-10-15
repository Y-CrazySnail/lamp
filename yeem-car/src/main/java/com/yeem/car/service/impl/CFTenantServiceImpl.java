package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.config.Constant;
import com.yeem.car.entity.BaseCF;
import com.yeem.car.entity.CFTenant;
import com.yeem.car.mapper.CFTenantMapper;
import com.yeem.car.service.ICFProductService;
import com.yeem.car.service.ICFTenantService;
import com.yeem.common.entity.BaseEntity;
import com.yeem.common.utils.OauthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CFTenantServiceImpl extends ServiceImpl<CFTenantMapper, CFTenant> implements ICFTenantService {

    @Autowired
    private CFTenantMapper tenantMapper;

    @Autowired
    private ICFProductService productService;

    @Override
    public <E extends IPage<CFTenant>> E page(E page) {
        LambdaQueryWrapper<CFTenant> queryWrapper = Wrappers.lambdaQuery(CFTenant.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        auth(queryWrapper);
        page = this.page(page, queryWrapper);
        for (CFTenant tenant : page.getRecords()) {
            productService.setProductList(tenant);
        }
        return page;
    }

    @Override
    public List<CFTenant> list() {
        LambdaQueryWrapper<CFTenant> queryWrapper = Wrappers.lambdaQuery(CFTenant.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        auth(queryWrapper);
        return super.list(queryWrapper);
    }

    @Override
    public boolean save(CFTenant tenant) {
        auth(tenant);
        return super.save(tenant);
    }

    @Override
    public boolean updateById(CFTenant tenant) {
        auth(tenant);
        return super.updateById(tenant);
    }

    @Override
    public boolean removeById(Serializable id) {
        LambdaUpdateWrapper<CFTenant> updateWrapper = Wrappers.lambdaUpdate(CFTenant.class);
        updateWrapper.set(CFTenant::getDeleteFlag, Constant.TRUE_NUMBER);
        updateWrapper.eq(CFTenant::getId, id);
        auth(updateWrapper);
        return this.update(updateWrapper);
    }

    /**
     * 查询时权限控制 只查询有权限租户的相关信息
     *
     * @param queryWrapper 查询包装
     * @param <T>          租户
     */
    public <T extends BaseCF> void auth(LambdaQueryWrapper<T> queryWrapper) {
        String username = OauthUtils.getUsername();
        LambdaQueryWrapper<CFTenant> tenantLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tenantLambdaQueryWrapper.like(CFTenant::getAuthorizedUsername, "|" + username + "|");
        List<CFTenant> tenantList = tenantMapper.selectList(tenantLambdaQueryWrapper);
        if (tenantList.isEmpty()) {
            queryWrapper.eq(BaseCF::getId, null);
        } else {
            queryWrapper.in(BaseCF::getTenantNo, tenantList.stream().map(CFTenant::getTenantNo).collect(Collectors.toList()));
        }
    }

    /**
     * 更新时权限控制 只查询有权限租户的相关信息
     *
     * @param updateWrapper 更新包装
     * @param <T>           租户
     */
    @Override
    public <T extends BaseCF> void auth(LambdaUpdateWrapper<T> updateWrapper) {
        String username = OauthUtils.getUsername();
        LambdaQueryWrapper<CFTenant> tenantLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tenantLambdaQueryWrapper.like(CFTenant::getAuthorizedUsername, "|" + username + "|");
        List<CFTenant> tenantList = tenantMapper.selectList(tenantLambdaQueryWrapper);
        if (tenantList.isEmpty()) {
            updateWrapper.eq(BaseCF::getId, null);
        } else {
            updateWrapper.in(BaseCF::getTenantNo, tenantList.stream().map(CFTenant::getTenantNo).collect(Collectors.toList()));
        }
    }

    /**
     * 操作实体类权限控制
     *
     * @param t   租户
     * @param <T> 租户
     */
    @Override
    public <T extends BaseCF> void auth(T t) {
        String username = OauthUtils.getUsername();
        LambdaQueryWrapper<CFTenant> tenantLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tenantLambdaQueryWrapper.like(CFTenant::getAuthorizedUsername, "|" + username + "|");
        List<CFTenant> tenantList = tenantMapper.selectList(tenantLambdaQueryWrapper);
        boolean authFlag = false;
        if (tenantList.isEmpty()) {
            authFlag = true;
        } else {
            if (tenantList.stream().noneMatch(tenant -> Objects.equals(tenant.getTenantNo(), t.getTenantNo()))) {
                authFlag = true;
            }
        }
        if (authFlag) {
            log.error("无该租户操作权限:{}", t.getTenantNo());
            throw new RuntimeException("无该租户操作权限-" + t.getTenantNo());
        }
    }
}
