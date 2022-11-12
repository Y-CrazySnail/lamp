package com.snail.proxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.proxy.entity.CommandConfig;
import com.snail.proxy.mapper.CommandConfigMapper;
import com.snail.proxy.service.ICommandConfigService;
import org.springframework.stereotype.Service;

@Service
public class CommandConfigServiceImpl extends ServiceImpl<CommandConfigMapper, CommandConfig> implements ICommandConfigService {

}
