package com.yeem.lamp.domain.service.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Product;
import com.yeem.lamp.domain.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageManageDomainService {

    @Autowired
    private PackageRepository packageRepository;

    public List<Product> list() {
        return packageRepository.list();
    }

    public IPage<Product> pages(int current, int size) {
        return packageRepository.pages(current, size);
    }

    public Product getById(Long id) {
        return packageRepository.getById(id);
    }

    public void updateById(Product packages) {
        packageRepository.updateById(packages);
    }

    public void insert(Product packages) {
        packageRepository.insert(packages);
    }

    public void deleteById(Long id) {
        packageRepository.deleteById(id);
    }
}
