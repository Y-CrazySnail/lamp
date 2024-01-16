package com.yeem.lamp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.AladdinServer;
import com.yeem.lamp.entity.AladdinService;

public interface IAladdinServerService extends IService<AladdinServer> {
    IPage<AladdinServer> pages(int current, int size);
}
