package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.Server;
import com.snail.mapper.ServerMapper;
import com.snail.service.IServerService;
import org.springframework.stereotype.Service;

@Service
public class ServerServiceImpl extends ServiceImpl<ServerMapper, Server> implements IServerService {

}
