package com.yeem.lamp.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceDomainService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<Services> listByMemberId(Long memberId) {
        List<Services> servicesList = serviceRepository.listByMemberId(memberId);
        servicesList.forEach(Services::dealSurplus);
        return servicesList;
    }

    public List<Services> list() {
        return serviceRepository.list();
    }

    public Services getById(Long id) {
        return serviceRepository.getById(id);
    }

    public Services getByUUID(String uuid) {
        Services services = serviceRepository.getByUUID(uuid);
        services.dealSurplus();
        return services;
    }

    public IPage<Services> pages(int current, int size, Long memberId, String status, String wechat, String email) {
        return serviceRepository.pages(current, size, memberId, status, wechat, email);
    }

    public void updateById(Services services) {
        services.calculateStatus();
        serviceRepository.updateById(services);
    }

    public void save(Services services) {
        serviceRepository.save(services);
    }

    public void removeById(Long id) {
        serviceRepository.removeById(id);
    }

    public void updateUUID(Long memberId, Long serviceId, String uuid) {
        serviceRepository.updateUUID(memberId, serviceId, uuid);
    }
}
