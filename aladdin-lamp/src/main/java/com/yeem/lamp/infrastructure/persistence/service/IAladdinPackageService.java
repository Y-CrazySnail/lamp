package com.yeem.lamp.infrastructure.persistence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinOrder;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinPackage;

public interface IAladdinPackageService extends IService<AladdinPackage> {
    IPage<AladdinPackage> pages(int current, int size);

    AladdinOrder order(AladdinOrder aladdinOrder);
}
