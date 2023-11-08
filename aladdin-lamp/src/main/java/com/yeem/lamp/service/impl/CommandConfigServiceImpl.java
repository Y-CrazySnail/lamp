package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.lamp.entity.CommandConfig;
import com.yeem.lamp.mapper.CommandConfigMapper;
import com.yeem.lamp.service.ICommandConfigService;
import org.springframework.stereotype.Service;

@Service
public class CommandConfigServiceImpl extends ServiceImpl<CommandConfigMapper, CommandConfig> implements ICommandConfigService {

}
