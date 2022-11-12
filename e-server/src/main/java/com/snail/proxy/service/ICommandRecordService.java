package com.snail.proxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.proxy.entity.CommandRecord;

public interface ICommandRecordService extends IService<CommandRecord> {
    CommandRecord get(String ip);
}
