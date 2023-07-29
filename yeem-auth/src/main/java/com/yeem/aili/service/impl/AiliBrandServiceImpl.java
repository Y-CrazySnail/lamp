package com.yeem.aili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.aili.entity.AiliBrand;
import com.yeem.aili.mapper.AiliBrandMapper;
import com.yeem.aili.service.IAiliBrandService;
import org.springframework.stereotype.Service;

@Service
public class AiliBrandServiceImpl extends ServiceImpl<AiliBrandMapper, AiliBrand> implements IAiliBrandService {
}
