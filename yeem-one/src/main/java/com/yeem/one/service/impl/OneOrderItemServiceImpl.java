package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.entity.OneOrderItem;
import com.yeem.one.mapper.OneOrderItemMapper;
import com.yeem.one.service.IOneOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OneOrderItemServiceImpl extends ServiceImpl<OneOrderItemMapper, OneOrderItem> implements IOneOrderItemService {

    @Autowired
    private OneOrderItemMapper mapper;

    @Override
    public List<OneOrderItem> listByOrderId(Long orderId) {
        LambdaQueryWrapper<OneOrderItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneOrderItem::getDeleteFlag, false);
        queryWrapper.eq(OneOrderItem::getOrderId, orderId);
        return mapper.selectList(queryWrapper);
    }
}
