package com.snail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.entity.CommandRecord;

public interface ICommandRecordService extends IService<CommandRecord> {
    CommandRecord get(String ip);
}
