package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneOrder;

public interface IOneOrderService extends IService<OneOrder> {
    OneOrder getByIdWithOther(Long id);
}
