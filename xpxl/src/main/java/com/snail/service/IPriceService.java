package com.snail.service;

import com.snail.entity.Price;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IPriceService extends IService<Price> {
    Price getByEntity(Price price);
}
