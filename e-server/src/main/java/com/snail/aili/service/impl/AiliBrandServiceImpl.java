package com.snail.aili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.aili.entity.AiliBrand;
import com.snail.aili.mapper.AiliBrandMapper;
import com.snail.aili.service.IAiliBrandService;
import org.springframework.stereotype.Service;

@Service
public class AiliBrandServiceImpl extends ServiceImpl<AiliBrandMapper, AiliBrand> implements IAiliBrandService {
}
