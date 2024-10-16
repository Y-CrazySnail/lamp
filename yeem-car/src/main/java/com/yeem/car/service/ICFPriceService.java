package com.yeem.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.CFPrice;
import com.yeem.car.entity.CFProduct;

public interface ICFPriceService extends IService<CFPrice> {
    void setPriceList(CFProduct product);
}
