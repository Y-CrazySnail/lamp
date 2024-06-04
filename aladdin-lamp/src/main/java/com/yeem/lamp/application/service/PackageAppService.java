package com.yeem.lamp.application.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.application.dto.PackageDTO;
import com.yeem.lamp.domain.entity.Package;
import com.yeem.lamp.domain.service.PackageDomainService;
import com.yeem.lamp.infrastructure.persistence.entity.PackageDo;
import com.yeem.lamp.security.Constant;
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
        QueryWrapper<PackageDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<PackageDo> page = new Page<>(current, size);
        return packageMapper.selectPage(page, queryWrapper);
    }
}
