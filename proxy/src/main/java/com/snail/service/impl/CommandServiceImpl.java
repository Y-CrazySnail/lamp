package com.snail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.chinaybop.entity.BaseEntity;
import com.snail.entity.Command;
import com.snail.mapper.CommandMapper;
import com.snail.service.ICommandService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommandServiceImpl extends ServiceImpl<CommandMapper, Command> implements ICommandService {

    @Override
    public Command get(String ip) {
        // 查询该IP是否有执行中的命令，如果有执行中的命令，不下派新命令
        QueryWrapper<Command> countQueryWrapper = new QueryWrapper<>();
        countQueryWrapper.eq(Command.Field.IP.getName(), ip);
        countQueryWrapper.eq(Command.Field.FLAG.getName(), 0);
        int count = super.count(countQueryWrapper);
        if (count > 0) {
            return null;
        }
        // 查询该IP需要执行的命令
        QueryWrapper<Command> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Command.Field.IP.getName(), ip);
        queryWrapper.eq(Command.Field.FLAG.getName(), -1);
        queryWrapper.orderByAsc(BaseEntity.BaseField.ID.getName());
        List<Command> commandList = super.list(queryWrapper);
        if (commandList.isEmpty()) {
            return null;
        } else {
            Command command = commandList.get(0);
            // 将该条记录状态置为执行中
            UpdateWrapper<Command> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq(BaseEntity.BaseField.ID.getName(), command.getId());
            updateWrapper.set(Command.Field.FLAG.getName(), 0);
            updateWrapper.set(BaseEntity.BaseField.UPDATE_TIME.getName(), LocalDateTime.now());
            super.update(updateWrapper);
            return command;
        }
    }
}
