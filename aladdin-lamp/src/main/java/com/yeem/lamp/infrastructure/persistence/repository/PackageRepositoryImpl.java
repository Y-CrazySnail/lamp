package com.yeem.lamp.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yeem.lamp.domain.entity.Package;
import com.yeem.lamp.domain.repository.PackageRepository;
import com.yeem.lamp.infrastructure.persistence.entity.PackageDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.PackageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PackageRepositoryImpl implements PackageRepository {

    @Autowired
    private PackageMapper packageMapper;

    @Override
    public List<Package> list() {
        LambdaQueryWrapper<PackageDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PackageDo::getDeleteFlag, false);
        List<PackageDo> packageDoList = packageMapper.selectList(queryWrapper);
        return packageDoList.stream().map(PackageDo::convertPackage).collect(Collectors.toList());
    }
}
