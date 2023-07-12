package com.snail.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.zero.entity.ZeroProduct;
import com.snail.zero.entity.ZeroProductImage;
import com.snail.zero.mapper.ZeroProductImageMapper;
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

    @Override
    public ZeroProduct getById(Long id) {
        ZeroProduct zeroProduct = zeroProductMapper.selectById(id);
        List<ZeroProductImage> zeroProductImageShowList = zeroProductImageService
                .listByProductIdAndType(zeroProduct.getId(), ZeroProductImage.Type.TYPE_SHOW.getType());
        zeroProduct.setZeroProductImageShowList(zeroProductImageShowList);
        List<ZeroProductImage> zeroProductImageSwiperList = zeroProductImageService
                .listByProductIdAndType(zeroProduct.getId(), ZeroProductImage.Type.TYPE_SWIPER.getType());
        zeroProduct.setZeroProductImageSwiperList(zeroProductImageSwiperList);
        List<ZeroProductImage> zeroProductImageDetailList = zeroProductImageService
                .listByProductIdAndType(zeroProduct.getId(), ZeroProductImage.Type.TYPE_DETAIL.getType());
        zeroProduct.setZeroProductImageDetailList(zeroProductImageDetailList);
        return zeroProduct;
    }

    @Override
    public void addProduct(ZeroProduct zeroProduct) {
        zeroProductMapper.insert(zeroProduct);
        zeroProductImageService.saveBatch(zeroProduct.getZeroProductImageShowList());
        zeroProductImageService.saveBatch(zeroProduct.getZeroProductImageSwiperList());
        zeroProductImageService.saveBatch(zeroProduct.getZeroProductImageDetailList());
    }

    @Override
    public void updateProduct(ZeroProduct zeroProduct) {
        zeroProductMapper.updateById(zeroProduct);
        zeroProductImageService.updateBatchById(zeroProduct.getZeroProductImageShowList());
        zeroProductImageService.updateBatchById(zeroProduct.getZeroProductImageSwiperList());
        zeroProductImageService.updateBatchById(zeroProduct.getZeroProductImageDetailList());
    }

    /**
     * 软删除
     * @param zeroProduct 产品信息
     */

    @Override
    public void removeProduct(ZeroProduct zeroProduct) {
        UpdateWrapper<ZeroProduct> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",zeroProduct.getId()).set("delete_flag", true);
        zeroProductMapper.update(null, updateWrapper);
        UpdateWrapper<ZeroProductImage> updateWrapperImg = new UpdateWrapper<>();
        updateWrapperImg.eq("product_id",zeroProduct.getId()).set("delete_flag", true);
        zeroProductImageService.update(null,updateWrapperImg);
    }
}
