package com.yeem.aili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.aili.entity.AiliModel;
import com.yeem.aili.mapper.AiliModelMapper;
import com.yeem.aili.service.IAiliModelService;
import org.springframework.stereotype.Service;

@Service
public class AiliModelServiceImpl extends ServiceImpl<AiliModelMapper, AiliModel> implements IAiliModelService {
}
