package com.yeem.lamp.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.domain.entity.Product;
import com.yeem.lamp.domain.repository.PackageRepository;
import com.yeem.lamp.infrastructure.persistence.entity.PackageDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.PackageMapper;
import com.yeem.lamp.security.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PackageRepositoryImpl implements PackageRepository {

    @Autowired
    private PackageMapper packageMapper;

    @Override
    public List<Product> list() {
        LambdaQueryWrapper<PackageDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PackageDo::getDeleteFlag, false);
        List<PackageDo> packageDoList = packageMapper.selectList(queryWrapper);
        return packageDoList.stream().map(PackageDo::convertPackage).collect(Collectors.toList());
    }

    @Override
    public IPage<Product> pages(int current, int size) {
        QueryWrapper<PackageDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<PackageDo> page = new Page<>(current, size);
        page = packageMapper.selectPage(page, queryWrapper);
        IPage<Product> res = new Page<>();
        res.setPages(page.getPages());
        res.setCurrent(page.getCurrent());
        res.setRecords(page.getRecords().stream().map(PackageDo::convertPackage).collect(Collectors.toList()));
        res.setSize(page.getSize());
        res.setTotal(page.getTotal());
        return res;
    }

    @Override
    public Product getById(Long id) {
        PackageDo packageDo = packageMapper.selectById(id);
        return packageDo.convertPackage();
    }

    public void updateById(Product packages) {
        PackageDo packageDo = PackageDo.init(packages);
        packageMapper.updateById(packageDo);
    }

    public void insert(Product packages) {
        PackageDo packageDo = PackageDo.init(packages);
        packageMapper.insert(packageDo);
    }

    public void deleteById(Long id) {
        LambdaUpdateWrapper<PackageDo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(PackageDo::getDeleteFlag, true);
        updateWrapper.eq(PackageDo::getId, id);
        packageMapper.update(null, updateWrapper);
    }
}
