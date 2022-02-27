package com.snail.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.demo.entity.Bill;
import com.snail.demo.mapper.BillMapper;
import com.snail.demo.service.IBillService;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {

}
