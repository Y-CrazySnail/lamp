package com.yeem.lamp.application.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.application.dto.ServiceDTO;
import com.yeem.lamp.domain.entity.NodeVmess;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.service.NodeVmessDomainService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceAppService {

    @Autowired
    private ServiceDomainService serviceDomainService;

    @Autowired
    private NodeVmessDomainService nodeVmessDomainService;

    public ServiceDTO getById(Long id) {
        Services services = serviceDomainService.getById(id);
        return new ServiceDTO(services);
    }

    public ServiceDTO getByUUID(String uuid) {
        Services services = serviceDomainService.getByUUID(uuid);
        return new ServiceDTO(services);
    }

    public IPage<ServiceDTO> pages(int current, int size,
                                   Long memberId, String status, String wechat, String email) {
        IPage<Services> page = serviceDomainService.pages(current, size, memberId, status, wechat, email);
        IPage<ServiceDTO> pageDTO = new Page<>();
        pageDTO.setPages(page.getPages());
        pageDTO.setTotal(page.getTotal());
        pageDTO.setSize(page.getSize());
        pageDTO.setCurrent(page.getCurrent());
        pageDTO.setRecords(page.getRecords().stream().map(ServiceDTO::new).collect(Collectors.toList()));
        return pageDTO;
    }

    /**
     * 根据服务ID、当前日期查询节点信息
     * @param serviceId 服务ID
     * @param currentDate 当前日期
     * @return 当前服务节点列表
     */
    public List<NodeVmess> list(Long serviceId, Date currentDate) {
        return nodeVmessDomainService.list(serviceId, currentDate);
    }

    public List<ServiceDTO> listByMemberId(Long memberId) {
        List<Services> servicesList = serviceDomainService.listByMemberId(memberId);
        return servicesList.stream().map(ServiceDTO::new).collect(Collectors.toList());
    }

    public void save(ServiceDTO serviceDTO) {
        Services services = serviceDTO.convertService();
        serviceDomainService.save(services);
    }

    public void updateById(ServiceDTO serviceDTO) {
        Services services = serviceDTO.convertService();
        serviceDomainService.updateById(services);
    }

    public void updateUUID(Long memberId, ServiceDTO serviceDTO) {
        serviceDomainService.updateUUID(memberId, serviceDTO.getId(), serviceDTO.getUuid());
    }

    public void removeById(Long id) {
        serviceDomainService.removeById(id);
    }

    public void syncStatus() {
        List<Services> servicesList = serviceDomainService.list();
        for (Services services : servicesList) {
            List<NodeVmess> nodeVmessList = nodeVmessDomainService.listByServiceId(services.getId());
            BigDecimal serviceUp = BigDecimal.valueOf(nodeVmessList.stream()
                    .mapToDouble(nodeVmess -> nodeVmess.getServiceUp() * nodeVmess.getMultiplyingPower()).sum() / 1024 / 1024 / 1024);
            BigDecimal serviceDown = BigDecimal.valueOf(nodeVmessList.stream()
                    .mapToDouble(nodeVmess -> nodeVmess.getServiceDown() * nodeVmess.getMultiplyingPower()).sum() / 1024 / 1024 / 1024);
            services.setServiceUp(serviceUp);
            services.setServiceDown(serviceDown);
            serviceDomainService.updateById(services);
        }
    }
}
