package com.snail.king.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.king.entity.KingPrice;
import com.snail.king.mapper.KingPriceMapper;
import com.snail.king.service.IKingPriceService;
import org.springframework.stereotype.Service;

@Service
public class KingPriceServiceImpl extends ServiceImpl<KingPriceMapper, KingPrice> implements IKingPriceService {

}
