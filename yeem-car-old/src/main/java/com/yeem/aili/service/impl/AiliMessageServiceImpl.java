package com.yeem.aili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.aili.entity.AiliMessage;
import com.yeem.aili.mapper.AiliMessageMapper;
import com.yeem.aili.service.IAiliMessageService;
import org.springframework.stereotype.Service;

@Service
public class AiliMessageServiceImpl extends ServiceImpl<AiliMessageMapper, AiliMessage> implements IAiliMessageService {
}
