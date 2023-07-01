package com.snail.zero.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.zero.entity.ZeroProductImage;
import com.snail.zero.mapper.ZeroProductImageMapper;
import com.snail.zero.service.IZeroProductImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param type      类型 0展示图 1轮播图 2详情图
     * @return 产品图片列表
     */
    @Override
    public List<ZeroProductImage> listByProductIdAndType(Long productId, Integer type) {
        return zeroProductImageMapper.selectByProductIdAndType(productId, type);
    }
}
