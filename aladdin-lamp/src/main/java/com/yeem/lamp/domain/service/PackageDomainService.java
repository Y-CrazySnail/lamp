package com.yeem.lamp.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.application.dto.PackageDTO;
import com.yeem.lamp.domain.entity.Package;
import com.yeem.lamp.domain.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageDomainService {

    @Autowired
    private PackageRepository packageRepository;

    public List<Package> list() {
        return packageRepository.list();
    }

    public IPage<Package> pages(int current, int size) {
        return packageRepository.pages(current, size);
    }

    public Package getById(Long id) {
        return packageRepository.getById(id);
    }
}
