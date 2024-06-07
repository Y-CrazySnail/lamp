package com.yeem.lamp.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;

import java.io.Serializable;
import java.util.List;

public interface ServiceRepository {
    Services getById(Long id);

    void save(Services services);

    void updateById(Services services);

    void removeById(Long id);

    List<Services> listByMemberId(Long memberId);

    List<Services> listValid();

    List<Services> list();

    boolean removeByMemberId(Serializable id);

    IPage<Services> pages(int current, int size, Long memberId, String status, String wechat, String email);

    ServiceDo getByUUID(String uuid);

    void refreshStatus();

    void updateUUID(Long memberId, Long serviceId, String uuid);
}
