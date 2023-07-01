package com.snail.zero.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.zero.entity.ZeroProduct;
import com.snail.zero.entity.ZeroProductImage;
import com.snail.zero.mapper.ZeroProductMapper;
import com.snail.zero.service.IZeroProductImageService;
import com.snail.zero.service.IZeroProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ZeroProductServiceImpl extends ServiceImpl<ZeroProductMapper, ZeroProduct> implements IZeroProductService {

    @Autowired
    private ZeroProductMapper zeroProductMapper;

    @Autowired
    private IZeroProductImageService zeroProductImageService;

    @Override
    public List<ZeroProduct> listByCategoryId(Long categoryId) {
        List<ZeroProduct> zeroProductList = zeroProductMapper.selectByCategoryId(categoryId);
        if (null != zeroProductList && !zeroProductList.isEmpty()) {
            zeroProductList.forEach(zeroProduct -> {
                List<ZeroProductImage> zeroProductImageShowList = zeroProductImageService
                        .listByProductIdAndType(zeroProduct.getId(), ZeroProductImage.Type.TYPE_SHOW.getType());
                zeroProduct.setZeroProductImageShowList(zeroProductImageShowList);
                List<ZeroProductImage> zeroProductImageSwiperList = zeroProductImageService
                        .listByProductIdAndType(zeroProduct.getId(), ZeroProductImage.Type.TYPE_SWIPER.getType());
                zeroProduct.setZeroProductImageSwiperList(zeroProductImageSwiperList);
                List<ZeroProductImage> zeroProductImageDetailList = zeroProductImageService
                        .listByProductIdAndType(zeroProduct.getId(), ZeroProductImage.Type.TYPE_DETAIL.getType());
                zeroProduct.setZeroProductImageDetailList(zeroProductImageDetailList);
            });
        }
        return zeroProductList;
    }
}
