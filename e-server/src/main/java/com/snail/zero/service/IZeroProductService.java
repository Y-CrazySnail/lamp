package com.snail.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.zero.entity.ZeroProduct;

import java.util.List;

public interface IZeroProductService extends IService<ZeroProduct> {
    List<ZeroProduct> listByCategoryId(Long categoryId);
}
