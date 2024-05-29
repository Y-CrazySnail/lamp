package com.yeem.lamp.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinNodeVmess;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinServer;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinService;

import java.util.List;

public interface IAladdinNodeVmessService extends IService<AladdinNodeVmess> {
    List<String> getNodeUrlList(String uuid, String label);
    List<AladdinNodeVmess> listByServerId(Long serverId, int year, int month);
    List<AladdinNodeVmess> listByServiceId(Long serviceId, int year, int month);
    List<AladdinNodeVmess> listByNodeType(String nodeType);
    boolean updateByServerId(Long serverId, String nodeType, String nodePs, Integer sort);
    void updateByValidServiceList(List<AladdinService> serviceList);
    void save(AladdinServer server, AladdinService service, int year, int month);
}
