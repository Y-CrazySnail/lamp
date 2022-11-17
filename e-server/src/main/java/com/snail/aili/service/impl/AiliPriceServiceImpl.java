package com.snail.aili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.aili.entity.AiliPrice;
import com.snail.aili.mapper.AiliPriceMapper;
import com.snail.aili.service.IAiliPriceService;
import org.springframework.stereotype.Service;

@Service
public class AiliPriceServiceImpl extends ServiceImpl<AiliPriceMapper, AiliPrice> implements IAiliPriceService {
}
