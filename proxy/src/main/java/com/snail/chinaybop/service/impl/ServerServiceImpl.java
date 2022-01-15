package com.snail.chinaybop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.chinaybop.entity.Server;
import com.snail.chinaybop.mapper.ServerMapper;
import com.snail.chinaybop.service.IServerService;
import org.springframework.stereotype.Service;

@Service
public class ServerServiceImpl extends ServiceImpl<ServerMapper, Server> implements IServerService {

}
