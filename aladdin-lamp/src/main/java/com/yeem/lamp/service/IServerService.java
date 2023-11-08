package com.yeem.lamp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.Server;

public interface IServerService extends IService<Server> {

    void refreshXray();
}
