package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.Message;
import com.snail.mapper.MessageMapper;
import com.snail.service.IMessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
