package com.yeem.lamp.domain.service.web;

import com.yeem.lamp.domain.entity.Product;
import com.yeem.lamp.infrastructure.persistence.repository.web.PackageWebRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageWebDomainService {

    @Autowired
    private PackageWebRepository packageRepository;

    public List<Product> list() {
        return packageRepository.list();
    }

    public Product getById(Long id) {
        return packageRepository.getById(id);
    }
}
