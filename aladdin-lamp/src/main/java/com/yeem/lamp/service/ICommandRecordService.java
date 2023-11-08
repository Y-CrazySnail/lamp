package com.yeem.lamp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.CommandRecord;

public interface ICommandRecordService extends IService<CommandRecord> {
    CommandRecord get(String ip);
}
