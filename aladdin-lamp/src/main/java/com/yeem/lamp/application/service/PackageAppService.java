package com.yeem.lamp.application.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.application.dto.PackageDTO;
import com.yeem.lamp.domain.entity.Package;
import com.yeem.lamp.domain.service.PackageDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageAppService {

    @Autowired
    private PackageDomainService packageDomainService;

    public List<PackageDTO> list() {
        List<Package> packageList = packageDomainService.list();
        return packageList.stream().map(PackageDTO::new).collect(Collectors.toList());
    }

    public IPage<PackageDTO> pages(int current, int size) {
        IPage<Package> page = packageDomainService.pages(current, size);
        IPage<PackageDTO> pageDTO = new Page<>();
        pageDTO.setPages(page.getPages());
        pageDTO.setTotal(page.getTotal());
        pageDTO.setSize(page.getSize());
        pageDTO.setCurrent(page.getCurrent());
        pageDTO.setRecords(page.getRecords().stream().map(PackageDTO::new).collect(Collectors.toList()));
        return pageDTO;
    }

    public PackageDTO getById(Long id) {
        Package packages = packageDomainService.getById(id);
        return new PackageDTO(packages);
    }
}
