package com.snail.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.demo.entity.BarberShop;
import com.snail.demo.mapper.BarberShopMapper;
import com.snail.demo.service.IBarberShopService;
import org.springframework.stereotype.Service;

@Service
public class BarberShopServiceImpl extends ServiceImpl<BarberShopMapper, BarberShop> implements IBarberShopService {

}
