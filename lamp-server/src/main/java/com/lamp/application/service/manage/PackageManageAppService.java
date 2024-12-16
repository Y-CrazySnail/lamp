package com.lamp.application.service.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lamp.application.dto.PackageDTO;
import com.lamp.domain.entity.Product;
import com.lamp.domain.service.manage.PackageManageDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageManageAppService {

    @Autowired
    private PackageManageDomainService packageDomainService;

    public List<PackageDTO> list() {
        List<Product> productList = packageDomainService.list();
        return productList.stream().map(PackageDTO::new).collect(Collectors.toList());
    }

    public IPage<PackageDTO> page(int current, int size) {
        IPage<Product> page = packageDomainService.pages(current, size);
        IPage<PackageDTO> pageDTO = new Page<>();
        pageDTO.setPages(page.getPages());
        pageDTO.setTotal(page.getTotal());
        pageDTO.setSize(page.getSize());
        pageDTO.setCurrent(page.getCurrent());
        pageDTO.setRecords(page.getRecords().stream().map(PackageDTO::new).collect(Collectors.toList()));
        return pageDTO;
    }

    public PackageDTO getById(Long id) {
        Product packages = packageDomainService.getById(id);
        return new PackageDTO(packages);
    }

    public void updateById(PackageDTO packageDTO) {
        packageDomainService.updateById(packageDTO.convertPackage());
    }

    public void insert(PackageDTO packageDTO) {
        packageDomainService.insert(packageDTO.convertPackage());
    }

    public void deleteById(Long id) {
        packageDomainService.deleteById(id);
    }
}
