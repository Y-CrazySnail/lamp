package com.yeem.lamp.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.infrastructure.persistence.entity.NodeVmessDo;
import com.yeem.lamp.infrastructure.persistence.entity.OrderDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServerDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;

import java.io.Serializable;
import java.util.List;

public interface OrderRepository {
    boolean removeByMemberId(Serializable id);
    IPage<OrderDo> pages(int current, int size);
    List<OrderDo> listByMemberId(Long memberId);
    void place(OrderDo orderDo);
    String pay(OrderDo orderDo);
    void finish(OrderDo orderDo);
}
