package com.snail.tank.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.tank.entity.TankPrice;

public interface ITankPriceService extends IService<TankPrice> {
    TankPrice getByEntity(TankPrice price);
}
