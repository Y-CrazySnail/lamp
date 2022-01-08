package com.snail.service;

import com.snail.entity.XpxlPrice;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IXpxlPriceService extends IService<XpxlPrice> {
    XpxlPrice getByEntity(XpxlPrice price);
}
