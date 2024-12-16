package com.lamp.application.service.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lamp.application.dto.ServiceDTO;
import com.lamp.domain.entity.Services;
import com.lamp.domain.service.manage.MemberManageDomainService;
import com.lamp.domain.service.manage.ServerManageDomainService;
import com.lamp.domain.service.manage.ServiceManageDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServiceManageAppService {

    @Autowired
    private MemberManageDomainService memberDomainService;
    @Autowired
    private ServiceManageDomainService serviceDomainService;
    @Autowired
    private ServerManageDomainService serverDomainService;
    @Autowired
    private ResourceLoader resourceLoader;

    public ServiceDTO getServiceById(Long id) {
        Services services = serviceDomainService.getById(id);
        return ServiceDTO.init(services);
    }

    public List<ServiceDTO> listServiceByMemberId(Long memberId) {
        List<Services> servicesList = serviceDomainService.listByMemberId(memberId);
        return servicesList.stream().map(ServiceDTO::init).collect(Collectors.toList());
    }

    public IPage<ServiceDTO> pageService(int current, int size, Long memberId, String status, String wechat, String email) {
        IPage<Services> page = serviceDomainService.pages(current, size, memberId, status, wechat, email);
        IPage<ServiceDTO> pageDTO = new Page<>();
        pageDTO.setPages(page.getPages());
        pageDTO.setTotal(page.getTotal());
        pageDTO.setSize(page.getSize());
        pageDTO.setCurrent(page.getCurrent());
        pageDTO.setRecords(page.getRecords().stream().map(ServiceDTO::init).collect(Collectors.toList()));
        return pageDTO;
    }

    public void saveService(ServiceDTO serviceDTO) {
        Services services = serviceDTO.convertService();
        serviceDomainService.save(services);
    }

    public void updateServiceById(ServiceDTO serviceDTO) {
        Services services = serviceDTO.convertService();
        serviceDomainService.updateById(services);
    }

    public void removeServiceById(Long id) {
        serviceDomainService.removeById(id);
    }
}
