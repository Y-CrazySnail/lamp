package com.snail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.Command;
import com.snail.mapper.CommandMapper;
import com.snail.service.ICommandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandServiceImpl extends ServiceImpl<CommandMapper, Command> implements ICommandService {

    @Override
    public Command get() {
        QueryWrapper<Command> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flag", -1);
        queryWrapper.orderByAsc("create_time");
        List<Command> commandList = super.list(queryWrapper);
        if (commandList.isEmpty()) {
            return null;
        } else {
            return commandList.get(0);
        }
    }
}
