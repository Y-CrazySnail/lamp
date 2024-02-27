package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.entity.OneSku;
import com.yeem.one.mapper.OneSkuMapper;
import com.yeem.one.service.IOneSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OneSkuServiceImpl extends ServiceImpl<OneSkuMapper, OneSku> implements IOneSkuService {

}
