package com.yeem.proxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.proxy.entity.CommandRecord;
import com.yeem.proxy.mapper.CommandRecordMapper;
import com.yeem.proxy.service.ICommandRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommandRecordServiceImpl extends ServiceImpl<CommandRecordMapper, CommandRecord> implements ICommandRecordService {

    @Override
    public CommandRecord get(String ip) {
        // 查询该IP是否有执行中的命令，如果有执行中的命令，不下派新命令
        QueryWrapper<CommandRecord> countQueryWrapper = new QueryWrapper<>();
        countQueryWrapper.eq(CommandRecord.Field.IP.getName(), ip);
        countQueryWrapper.eq(CommandRecord.Field.FLAG.getName(), 0);
        int count = super.count(countQueryWrapper);
        if (count > 0) {
            return null;
        }
        // 查询该IP需要执行的命令
        QueryWrapper<CommandRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CommandRecord.Field.IP.getName(), ip);
        queryWrapper.eq(CommandRecord.Field.FLAG.getName(), -1);
        queryWrapper.orderByAsc(BaseEntity.BaseField.ID.getName());
        List<CommandRecord> commandRecordList = super.list(queryWrapper);
        if (commandRecordList.isEmpty()) {
            return null;
        } else {
            CommandRecord commandRecord = commandRecordList.get(0);
            // 将该条记录状态置为执行中
            UpdateWrapper<CommandRecord> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq(BaseEntity.BaseField.ID.getName(), commandRecord.getId());
            updateWrapper.set(CommandRecord.Field.FLAG.getName(), 0);
            updateWrapper.set(CommandRecord.Field.EXECUTE_START_TIME.getName(), LocalDateTime.now());
            updateWrapper.set(BaseEntity.BaseField.UPDATE_TIME.getName(), LocalDateTime.now());
            super.update(updateWrapper);
            return commandRecord;
        }
    }
}
