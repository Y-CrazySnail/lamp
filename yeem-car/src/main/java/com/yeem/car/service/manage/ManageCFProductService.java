package com.yeem.car.service.manage;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.config.Constant;
import com.yeem.car.entity.CFProduct;
import com.yeem.car.entity.CFTenant;
import com.yeem.car.mapper.CFProductMapper;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class ManageCFProductService extends ServiceImpl<CFProductMapper, CFProduct> {

    @Autowired
    private ManageCFTenantService tenantService;

    @Autowired
    private ManageCFPriceService priceService;

    @Autowired
    private ManageCFPriceConfigService priceConfigService;

    @Autowired
    private CFProductMapper productMapper;

    public List<CFProduct> list(String tenantNo) {
        LambdaQueryWrapper<CFProduct> queryWrapper = Wrappers.lambdaQuery(CFProduct.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(CFProduct::getTenantNo, tenantNo);
        List<CFProduct> productList = productMapper.selectList(queryWrapper);
        for (CFProduct product : productList) {
            priceService.setPriceList(product);
            priceConfigService.setPriceConfigList(product);
        }
        return productList;
    }

    public IPage<CFProduct> page(IPage<CFProduct> page, String tenantNo) {
        LambdaQueryWrapper<CFProduct> queryWrapper = Wrappers.lambdaQuery(CFProduct.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        tenantService.auth(queryWrapper);
        if (StrUtil.isNotEmpty(tenantNo)) {
            queryWrapper.eq(CFProduct::getTenantNo, tenantNo);
        }
        page = this.page(page, queryWrapper);
        return page;
    }

    @Override
    public CFProduct getById(Serializable id) {
        CFProduct product = super.getById(id);
        priceService.setPriceList(product);
        priceConfigService.setPriceConfigList(product);
        return product;
    }

    @Override
    public boolean save(CFProduct product) {
        tenantService.auth(product);
        return super.save(product);
    }

    @Override
    public boolean updateById(CFProduct product) {
        tenantService.auth(product);
        priceService.savePriceList(product);
        priceConfigService.savePriceConfigList(product);
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

    public void setProductList(CFTenant tenant) {
        LambdaQueryWrapper<CFProduct> queryWrapper = new LambdaQueryWrapper<>(CFProduct.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(CFProduct::getTenantNo, tenant.getTenantNo());
        List<CFProduct> productList = productMapper.selectList(queryWrapper);
        tenant.setProductList(productList);
    }

}
