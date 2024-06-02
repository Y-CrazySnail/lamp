package com.yeem.lamp.domain.service;

import com.yeem.lamp.domain.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceDomainService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<com.yeem.lamp.domain.entity.Service> listByMemberId(Long memberId) {
        return serviceRepository.listByMemberId(memberId);
    }
}
