package com.yeem.lamp.domain.service;

import com.yeem.lamp.domain.objvalue.NodeVmess;
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

    public List<NodeVmess> listValidByServerId(Long serverId, Date currentDate) {
        return nodeVmessRepository.list("private", serverId, currentDate);
    }

    public void expired() {
        nodeVmessRepository.expired();
    }

    public int count(Long serverId, Long serviceId, String uuid, Date currentDate) {
        return nodeVmessRepository.count(serverId, serviceId, uuid, currentDate);
    }

    public List<NodeVmess> list(Long serverId, Long serviceId, Date currentDate) {
        return nodeVmessRepository.list(serverId, serviceId, currentDate);
    }

    public List<NodeVmess> list(Long serviceId, Date currentDate) {
        return nodeVmessRepository.list(serviceId, currentDate);
    }

    public void save(NodeVmess nodeVmess) {
        nodeVmessRepository.insert(nodeVmess);
    }

    public void updateById(NodeVmess nodeVmess) {
        nodeVmessRepository.updateById(nodeVmess);
    }
}
