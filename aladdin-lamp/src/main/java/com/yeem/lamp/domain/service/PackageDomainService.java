package com.yeem.lamp.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.application.dto.PackageDTO;
import com.yeem.lamp.domain.entity.Package;
import com.yeem.lamp.domain.repository.PackageRepository;
import com.yeem.lamp.infrastructure.persistence.entity.PackageDo;
import com.yeem.lamp.security.Constant;
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

    public IPage<PackageDTO> pages(int current, int size) {
        packageRepository.pages();
        QueryWrapper<PackageDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<PackageDo> page = new Page<>(current, size);
        return packageMapper.selectPage(page, queryWrapper);
    }
}
