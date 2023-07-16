package com.snail.zero.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.zero.entity.ZeroOrderItem;
import com.snail.zero.mapper.ZeroOrderItemMapper;
import com.snail.zero.service.IZeroOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ZeroOrderItemServiceImpl extends ServiceImpl<ZeroOrderItemMapper, ZeroOrderItem> implements IZeroOrderItemService {

}
