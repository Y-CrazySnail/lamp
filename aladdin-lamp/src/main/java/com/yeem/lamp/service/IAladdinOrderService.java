package com.yeem.lamp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.AladdinOrder;

import java.io.Serializable;
import java.util.List;

public interface IAladdinOrderService extends IService<AladdinOrder> {
    boolean removeByMemberId(Serializable id);
    IPage<AladdinOrder> pages(int current, int size);
    List<AladdinOrder> listByMemberId(Long memberId);
    void place(AladdinOrder aladdinOrder);
    void finish(AladdinOrder aladdinOrder);
}
