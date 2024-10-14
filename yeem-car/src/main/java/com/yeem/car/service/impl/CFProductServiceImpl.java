package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.config.Constant;
import com.yeem.car.entity.CFProduct;
import com.yeem.car.entity.CFTenant;
import com.yeem.car.mapper.CFProductMapper;
import com.yeem.car.service.ICFProductService;
import com.yeem.car.service.ICFTenantService;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class CFProductServiceImpl extends ServiceImpl<CFProductMapper, CFProduct> implements ICFProductService {

    @Autowired
    private ICFTenantService tenantService;

    @Autowired
    private CFProductMapper productMapper;

    @Override
    public <E extends IPage<CFProduct>> E page(E page) {
        LambdaQueryWrapper<CFProduct> queryWrapper = new LambdaQueryWrapper<>();
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        tenantService.auth(queryWrapper);
        page = this.page(page, queryWrapper);
        return page;
    }

    @Override
    public boolean save(CFProduct product) {
        tenantService.auth(product);
        return super.save(product);
    }

    @Override
    public boolean updateById(CFProduct product) {
        tenantService.auth(product);
        return super.updateById(product);
    }

    @Override
    public boolean removeById(Serializable id) {
        LambdaUpdateWrapper<CFProduct> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(CFProduct::getDeleteFlag, Constant.TRUE_NUMBER);
        updateWrapper.eq(CFProduct::getId, id);
        tenantService.auth(updateWrapper);
        return this.update(updateWrapper);
    }

    @Override
    public void setProductList(CFTenant tenant) {
        LambdaQueryWrapper<CFProduct> queryWrapper = new LambdaQueryWrapper<>();
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(CFProduct::getTenantNo, tenant.getTenantNo());
        List<CFProduct> productList = productMapper.selectList(queryWrapper);
        tenant.setProductList(productList);
    }
}
