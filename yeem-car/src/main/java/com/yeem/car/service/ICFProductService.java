package com.yeem.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.CFProduct;
import com.yeem.car.entity.CFTenant;

public interface ICFProductService extends IService<CFProduct> {
    void setProductList(CFTenant tenant);
}
