package com.yeem.lamp.application.service.web;

import com.yeem.lamp.application.dto.PackageDTO;
import com.yeem.lamp.domain.entity.Product;
import com.yeem.lamp.domain.service.web.PackageWebDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageWebAppService {

    @Autowired
    private PackageWebDomainService packageDomainService;

    public List<PackageDTO> list() {
        List<Product> productList = packageDomainService.list();
        return productList.stream().map(PackageDTO::new).collect(Collectors.toList());
    }
}
