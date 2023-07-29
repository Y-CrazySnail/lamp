package com.yeem.chinaybop.service;

import com.yeem.chinaybop.entity.ChinaybopPrice;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IChinaybopPriceService extends IService<ChinaybopPrice> {
    ChinaybopPrice getByEntity(ChinaybopPrice price);
}
