package com.yeem.car.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.CFProduct;
import com.yeem.car.entity.CFTenant;

public interface ICFProductService extends IService<CFProduct> {
    IPage<CFProduct> page(IPage<CFProduct> page, String tenantNo);

    void setProductList(CFTenant tenant);
}
