package com.snail.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.zero.entity.ZeroProductImage;

import java.util.List;

public interface IZeroProductImageService extends IService<ZeroProductImage> {
    List<ZeroProductImage> listByProductIdAndType(Long productId, Integer type);
}
