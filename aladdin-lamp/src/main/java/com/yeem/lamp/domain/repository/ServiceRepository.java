package com.yeem.lamp.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Services;

import java.util.List;

public interface ServiceRepository {
    Services getById(Long id);

    Services getByUUID(String uuid);

    void save(Services services);

    void updateById(Services services);

    void removeById(Long id);

    List<Services> listByMemberId(Long memberId);

    List<Services> list();

    IPage<Services> pages(int current, int size, Long memberId, String status, String wechat, String email);

    void updateUUID(Long memberId, Long serviceId, String uuid);
}
