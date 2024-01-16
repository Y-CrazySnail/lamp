package com.yeem.lamp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.AladdinOrder;

import java.io.Serializable;

public interface IAladdinOrderService extends IService<AladdinOrder> {
    boolean removeByMemberId(Serializable id);
    IPage<AladdinOrder> pages(int current, int size);
}
