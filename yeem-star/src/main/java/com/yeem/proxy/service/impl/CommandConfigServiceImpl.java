package com.yeem.proxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.proxy.entity.CommandConfig;
import com.yeem.proxy.mapper.CommandConfigMapper;
import com.yeem.proxy.service.ICommandConfigService;
import org.springframework.stereotype.Service;

@Service
public class CommandConfigServiceImpl extends ServiceImpl<CommandConfigMapper, CommandConfig> implements ICommandConfigService {

}
