package com.snail.aili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.aili.entity.AiliMessage;
import com.snail.aili.mapper.AiliMessageMapper;
import com.snail.aili.service.IAiliMessageService;
import org.springframework.stereotype.Service;

@Service
public class AiliMessageServiceImpl extends ServiceImpl<AiliMessageMapper, AiliMessage> implements IAiliMessageService {
}
