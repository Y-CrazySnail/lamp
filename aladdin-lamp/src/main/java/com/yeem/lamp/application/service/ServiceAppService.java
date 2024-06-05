package com.yeem.lamp.application.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.application.dto.ServiceDTO;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.service.ServiceDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceAppService {

    @Autowired
    private ServiceDomainService serviceDomainService;

    public ServiceDTO getById(Long id) {
        Services services = serviceDomainService.getById(id);
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

    public void updateById(ServiceDTO serviceDTO) {
        Services services = serviceDTO.convertService();
        serviceDomainService.updateById(services);
    }

    public void save(ServiceDTO serviceDTO) {
        Services services = serviceDTO.convertService();
        serviceDomainService.save(services);
    }

    public void removeById(Long id) {
        serviceDomainService.removeById(id);
    }

    public List<ServiceDTO> listByMemberId(Long memberId) {
        List<Services> servicesList = serviceDomainService.listByMemberId(memberId);
        return servicesList.stream().map(ServiceDTO::new).collect(Collectors.toList());
    }

    public void updateUUID(Long memberId, ServiceDTO serviceDTO) {
        serviceDomainService.updateUUID(memberId, serviceDTO.getId(), serviceDTO.getUuid());
    }
}
