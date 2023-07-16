package com.snail.zero.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.zero.entity.ZeroCart;
import com.snail.zero.mapper.ZeroCartMapper;
import com.snail.zero.service.IZeroCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ZeroCartServiceImpl extends ServiceImpl<ZeroCartMapper, ZeroCart> implements IZeroCartService {

}
