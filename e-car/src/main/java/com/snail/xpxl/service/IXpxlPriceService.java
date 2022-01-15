package com.snail.xpxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.xpxl.entity.XpxlPrice;

public interface IXpxlPriceService extends IService<XpxlPrice> {
    XpxlPrice getByEntity(XpxlPrice price);
}
