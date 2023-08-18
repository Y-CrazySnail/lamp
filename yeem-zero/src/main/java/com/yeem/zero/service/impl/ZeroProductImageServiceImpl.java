package com.yeem.zero.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.zero.entity.ZeroProductImage;
import com.yeem.zero.mapper.ZeroProductImageMapper;
import com.yeem.zero.service.IZeroProductImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class ZeroProductImageServiceImpl extends ServiceImpl<ZeroProductImageMapper, ZeroProductImage> implements IZeroProductImageService {

    @Autowired
    private ZeroProductImageMapper zeroProductImageMapper;

    /**
     * 查询产品图片列表 根据产品ID和类型
     *
     * @param productId 产品ID
     * @return 产品图片列表
     */
    @Override
    public List<ZeroProductImage> listByProductId(Long productId) {
        return zeroProductImageMapper.selectByProductId(productId);
    }

    @Override
    @DS("zero")
    public boolean saveBatch(Collection<ZeroProductImage> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @DS("zero")
    public boolean updateBatchById(Collection<ZeroProductImage> entityList) {
        return super.updateBatchById(entityList);
    }
}
