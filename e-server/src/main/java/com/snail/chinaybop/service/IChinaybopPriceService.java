package com.snail.chinaybop.service;

import com.snail.chinaybop.entity.ChinaybopPrice;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IChinaybopPriceService extends IService<ChinaybopPrice> {
    ChinaybopPrice getByEntity(ChinaybopPrice price);
}
