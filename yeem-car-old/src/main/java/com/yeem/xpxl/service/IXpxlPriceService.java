package com.yeem.xpxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.xpxl.entity.XpxlPrice;

public interface IXpxlPriceService extends IService<XpxlPrice> {
    XpxlPrice getByEntity(XpxlPrice price);
}
