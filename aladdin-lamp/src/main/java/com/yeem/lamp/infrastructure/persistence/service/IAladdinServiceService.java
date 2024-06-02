package com.yeem.lamp.infrastructure.persistence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;

import java.io.Serializable;
import java.util.List;

public interface IAladdinServiceService extends IService<ServiceDo> {
    List<ServiceDo> listValid();
    List<ServiceDo> listByMemberId(Long memberId);

    boolean removeByMemberId(Serializable id);

    IPage<ServiceDo> pages(int current, int size,
                           Long memberId,
                           String status,
                           String wechat,
                           String email);

    ServiceDo getByUUID(String uuid);

    void refreshStatus();
}
