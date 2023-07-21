package com.yeem.aili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.aili.entity.AiliPrice;
import com.yeem.aili.mapper.AiliPriceMapper;
import com.yeem.aili.service.IAiliPriceService;
import org.springframework.stereotype.Service;

@Service
public class AiliPriceServiceImpl extends ServiceImpl<AiliPriceMapper, AiliPrice> implements IAiliPriceService {
}
