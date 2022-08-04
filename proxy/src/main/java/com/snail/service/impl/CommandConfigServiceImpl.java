package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.CommandConfig;
import com.snail.mapper.CommandConfigMapper;
import com.snail.service.ICommandConfigService;
import org.springframework.stereotype.Service;

@Service
public class CommandConfigServiceImpl extends ServiceImpl<CommandConfigMapper, CommandConfig> implements ICommandConfigService {

}
