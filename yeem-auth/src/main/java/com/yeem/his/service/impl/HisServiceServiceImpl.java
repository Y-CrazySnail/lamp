package com.yeem.his.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.his.entity.HisService;
import com.yeem.his.mapper.HisServiceMapper;
import com.yeem.his.service.IHisServiceService;
import org.springframework.stereotype.Service;

@Service
public class HisServiceServiceImpl extends ServiceImpl<HisServiceMapper, HisService> implements IHisServiceService {

}
