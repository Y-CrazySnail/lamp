package com.lamp.infrastructure.persistence.repository.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lamp.common.entity.BaseEntity;
import com.lamp.domain.entity.Product;
import com.lamp.infrastructure.persistence.entity.ProductDo;
import com.lamp.infrastructure.persistence.repository.mapper.PackageMapper;
import com.lamp.security.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PackageManageRepository {

    @Autowired
    private PackageMapper packageMapper;

    public List<Product> list() {
        LambdaQueryWrapper<ProductDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductDo::getDeleteFlag, false);
        List<ProductDo> productDoList = packageMapper.selectList(queryWrapper);
        return productDoList.stream().map(ProductDo::convertProduct).collect(Collectors.toList());
    }

    public IPage<Product> pages(int current, int size) {
        QueryWrapper<ProductDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<ProductDo> page = new Page<>(current, size);
        page = packageMapper.selectPage(page, queryWrapper);
        IPage<Product> res = new Page<>();
        res.setPages(page.getPages());
        res.setCurrent(page.getCurrent());
        res.setRecords(page.getRecords().stream().map(ProductDo::convertProduct).collect(Collectors.toList()));
        res.setSize(page.getSize());
        res.setTotal(page.getTotal());
        return res;
    }

    public Product getById(Long id) {
        ProductDo productDo = packageMapper.selectById(id);
        return productDo.convertProduct();
    }

    public void updateById(Product packages) {
        ProductDo productDo = ProductDo.init(packages);
        packageMapper.updateById(productDo);
    }

    public void insert(Product packages) {
        ProductDo productDo = ProductDo.init(packages);
        packageMapper.insert(productDo);
    }

    public void deleteById(Long id) {
        LambdaUpdateWrapper<ProductDo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ProductDo::getDeleteFlag, true);
        updateWrapper.eq(ProductDo::getId, id);
        packageMapper.update(null, updateWrapper);
    }
}
