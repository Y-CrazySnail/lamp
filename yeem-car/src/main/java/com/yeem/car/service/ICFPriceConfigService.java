package com.yeem.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.CFPriceConfig;
import com.yeem.car.entity.CFProduct;

public interface ICFPriceConfigService extends IService<CFPriceConfig> {
    void setPriceConfigList(CFProduct product);
}
