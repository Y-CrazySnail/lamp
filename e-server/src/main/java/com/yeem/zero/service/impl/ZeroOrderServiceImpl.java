package com.yeem.zero.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.mapper.ZeroOrderMapper;
import com.yeem.zero.service.IZeroOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ZeroOrderServiceImpl extends ServiceImpl<ZeroOrderMapper, ZeroOrder> implements IZeroOrderService {

}
