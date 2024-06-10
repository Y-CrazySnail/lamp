package com.yeem.lamp.domain.repository;

import com.yeem.lamp.domain.entity.NodeVmess;

import java.util.Date;
import java.util.List;

public interface NodeVmessRepository {
    List<NodeVmess> list(String nodeType, Long serverId, Date currentDate);
    List<NodeVmess> list(Long serverId, Long serviceId, Date currentDate);

    List<NodeVmess> listByServiceId(Long serviceId);

    List<NodeVmess> list(Long serviceId, Date currentDate);

    void expired();

    int count(Long serverId, Long serviceId, String uuid, Date currentDate);

    void insert(NodeVmess nodeVmess);

    void updateById(NodeVmess nodeVmess);
}
