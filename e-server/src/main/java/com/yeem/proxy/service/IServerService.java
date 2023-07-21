package com.yeem.proxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.proxy.entity.Server;

public interface IServerService extends IService<Server> {

    void refreshXray();
}
