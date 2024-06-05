package com.yeem.lamp.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Package;
import com.yeem.lamp.domain.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeVmessDomainService {

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

    public void updateById(Package packages) {
        packageRepository.updateById(packages);
    }

    public void insert(Package packages) {
        packageRepository.insert(packages);
    }

    public void deleteById(Long id) {
        packageRepository.deleteById(id);
    }
}
