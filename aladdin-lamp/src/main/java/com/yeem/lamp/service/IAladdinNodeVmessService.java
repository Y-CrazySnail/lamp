package com.yeem.lamp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.lamp.entity.AladdinNodeVmess;

import java.util.List;

public interface IAladdinNodeVmessService extends IService<AladdinNodeVmess> {
    List<String> getNodeUrlList(String uuid, String label);
    List<AladdinNodeVmess> listByServerId(Long serverId, int year, int month);
    List<AladdinNodeVmess> listByServiceId(Long serviceId, int year, int month);
    List<AladdinNodeVmess> listByNodeType(String nodeType);
    boolean updateByServerId(Long serverId, String nodeType, String nodePs, Integer sort);
}
