package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroProduct;
import com.yeem.zero.entity.ZeroProductImage;

import java.util.List;

public interface IZeroProductImageService extends IService<ZeroProductImage> {
    List<ZeroProductImage> listByProductId(Long productId);
    void setProductImage(ZeroProduct zeroProduct);
}
