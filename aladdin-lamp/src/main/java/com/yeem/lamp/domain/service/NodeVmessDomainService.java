package com.yeem.lamp.domain.service;

import com.yeem.lamp.domain.entity.NodeVmess;
import com.yeem.lamp.domain.repository.NodeVmessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NodeVmessDomainService {

    @Autowired
    private NodeVmessRepository nodeVmessRepository;

    public List<NodeVmess> listByServiceId(Long serviceId) {
        return nodeVmessRepository.listByServiceId(serviceId);
    }

    public void expired() {
        nodeVmessRepository.expired();
    }

    public int count(Long serverId, Long serviceId, Date currentDate) {
        return nodeVmessRepository.count(serverId, serviceId, currentDate);
    }
}
