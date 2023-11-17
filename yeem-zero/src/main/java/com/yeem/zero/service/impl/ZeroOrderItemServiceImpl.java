package com.yeem.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.zero.entity.ZeroOrderItem;
import com.yeem.zero.entity.ZeroProduct;
import com.yeem.zero.mapper.ZeroOrderItemMapper;
import com.yeem.zero.service.IZeroOrderItemService;
import com.yeem.zero.service.IZeroProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ZeroOrderItemServiceImpl extends ServiceImpl<ZeroOrderItemMapper, ZeroOrderItem> implements IZeroOrderItemService {

    @Autowired
    private ZeroOrderItemMapper zeroOrderItemMapper;

    @Override
    public List<ZeroOrderItem> listById(Long orderId) {
        QueryWrapper<ZeroOrderItem> zeroOrderItemQueryWrapper = new QueryWrapper<>();
        zeroOrderItemQueryWrapper.eq("order_id", orderId);
        return zeroOrderItemMapper.selectList(zeroOrderItemQueryWrapper);
    }
}
