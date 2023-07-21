package com.yeem.proxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.proxy.entity.CommandRecord;

public interface ICommandRecordService extends IService<CommandRecord> {
    CommandRecord get(String ip);
}
