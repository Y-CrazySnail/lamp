package com.snail.proxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.proxy.entity.Server;

public interface IServerService extends IService<Server> {

    void refreshXray();
}
