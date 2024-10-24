package com.yeem.lamp.infrastructure.persistence.repository.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yeem.lamp.domain.entity.Product;
import com.yeem.lamp.infrastructure.persistence.entity.ProductDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.PackageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PackageWebRepository {

    @Autowired
    private PackageMapper packageMapper;

    public List<Product> list() {
        LambdaQueryWrapper<ProductDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductDo::getDeleteFlag, false);
        List<ProductDo> productDoList = packageMapper.selectList(queryWrapper);
        return productDoList.stream().map(ProductDo::convertProduct).collect(Collectors.toList());
    }

    public Product getById(Long id) {
        ProductDo productDo = packageMapper.selectById(id);
        return productDo.convertProduct();
    }
}
