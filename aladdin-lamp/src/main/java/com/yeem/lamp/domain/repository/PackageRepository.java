package com.yeem.lamp.domain.repository;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.application.dto.PackageDTO;
import com.yeem.lamp.domain.entity.Package;
import com.yeem.lamp.infrastructure.persistence.entity.PackageDo;

import java.util.List;

public interface PackageRepository {
    List<Package> list();
    IPage<Package> pages(int current, int size);
    Package getById(Long id);
    void updateById(Package packages);
    void insert(Package packages);
    void deleteById(Long id);
}
