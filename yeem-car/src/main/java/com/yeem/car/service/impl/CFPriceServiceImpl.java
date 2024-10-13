package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.CFPrice;
import com.yeem.car.mapper.CFPriceMapper;
import com.yeem.car.service.ICFPriceService;
import org.springframework.stereotype.Service;

@Service
public class CFPriceServiceImpl extends ServiceImpl<CFPriceMapper, CFPrice> implements ICFPriceService {

}
