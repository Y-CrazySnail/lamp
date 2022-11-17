package com.snail.aili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.aili.entity.AiliModel;
import com.snail.aili.mapper.AiliModelMapper;
import com.snail.aili.service.IAiliModelService;
import org.springframework.stereotype.Service;

@Service
public class AiliModelServiceImpl extends ServiceImpl<AiliModelMapper, AiliModel> implements IAiliModelService {
}
