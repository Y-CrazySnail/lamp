package com.yeem.lamp.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Product;

import java.util.List;

public interface PackageRepository {
    List<Product> list();
    IPage<Product> pages(int current, int size);
    Product getById(Long id);
    void updateById(Product packages);
    void insert(Product packages);
    void deleteById(Long id);
}
