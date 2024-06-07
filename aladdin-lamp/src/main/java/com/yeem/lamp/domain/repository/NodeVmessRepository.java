package com.yeem.lamp.domain.repository;

import com.yeem.lamp.domain.entity.NodeVmess;
import com.yeem.lamp.infrastructure.persistence.entity.NodeVmessDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServerDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;

import java.util.Date;
import java.util.List;

public interface NodeVmessRepository {
    List<NodeVmess> listByServiceId(Long serviceId);
    void expired();
    int count(Long serverId, Long serviceId, Date currentDate);
}
