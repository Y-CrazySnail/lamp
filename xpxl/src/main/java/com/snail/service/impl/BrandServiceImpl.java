package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.Brand;
import com.snail.entity.Price;
import com.snail.mapper.BrandMapper;
import com.snail.mapper.PriceMapper;
import com.snail.service.IBrandService;
import com.snail.service.IPriceService;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

}
