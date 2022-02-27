package com.snail.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.demo.entity.ChildBill;
import com.snail.demo.mapper.ChildBillMapper;
import com.snail.demo.service.IChildBillService;
import org.springframework.stereotype.Service;

@Service
public class ChildBillServiceImpl extends ServiceImpl<ChildBillMapper, ChildBill> implements IChildBillService {

}
