package com.yeem.king.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.king.entity.KingPrice;
import com.yeem.king.mapper.KingPriceMapper;
import com.yeem.king.service.IKingPriceService;
import org.springframework.stereotype.Service;

@Service
public class KingPriceServiceImpl extends ServiceImpl<KingPriceMapper, KingPrice> implements IKingPriceService {

}
