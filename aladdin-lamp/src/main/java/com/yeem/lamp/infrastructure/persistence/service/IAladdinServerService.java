package com.yeem.lamp.infrastructure.persistence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinServer;

public interface IAladdinServerService extends IService<AladdinServer> {
    IPage<AladdinServer> pages(int current, int size);
}
