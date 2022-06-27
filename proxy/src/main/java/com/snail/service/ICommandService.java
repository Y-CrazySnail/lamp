package com.snail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.entity.Command;

public interface ICommandService extends IService<Command> {
    Command get();
}
