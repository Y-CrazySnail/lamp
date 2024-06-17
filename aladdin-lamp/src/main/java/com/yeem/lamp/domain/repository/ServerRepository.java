package com.yeem.lamp.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Server;

import java.util.List;

public interface ServerRepository {
    IPage<Server> pages(int current, int size);
    List<Server> list();
    Server getById(Long id);
    void save(Server server);
    void updateById(Server server);
    void removeById(Long id);
}
