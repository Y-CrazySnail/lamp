package com.yeem.tank.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.tank.entity.TankPrice;

public interface ITankPriceService extends IService<TankPrice> {
    TankPrice getByEntity(TankPrice price);
}
