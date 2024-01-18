package com.yeem.lamp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.AladdinOrder;
import com.yeem.lamp.entity.AladdinPackage;
import com.yeem.lamp.entity.AladdinServer;

public interface IAladdinPackageService extends IService<AladdinPackage> {
    IPage<AladdinPackage> pages(int current, int size);

    AladdinOrder order(AladdinOrder aladdinOrder);
}
