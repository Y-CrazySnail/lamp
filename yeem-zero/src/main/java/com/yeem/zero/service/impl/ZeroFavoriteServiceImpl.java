package com.yeem.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.zero.config.Constant;
import com.yeem.zero.entity.ZeroFavorite;
import com.yeem.zero.mapper.ZeroFavoriteMapper;
import com.yeem.zero.service.IZeroFavoriteService;
import com.yeem.zero.service.IZeroProductService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ZeroFavoriteServiceImpl extends ServiceImpl<ZeroFavoriteMapper, ZeroFavorite> implements IZeroFavoriteService {

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    @Autowired
    private IZeroProductService zeroProductService;

    @Override
    public List<ZeroFavorite> listByUserId(Long userId) {
        QueryWrapper<ZeroFavorite> zeroFavoriteQueryWrapper = new QueryWrapper<>();
        zeroFavoriteQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
        zeroFavoriteQueryWrapper.eq("user_id", userId);
        List<ZeroFavorite> zeroFavoriteList = baseMapper.selectList(zeroFavoriteQueryWrapper);
        zeroFavoriteList.forEach(zeroFavorite -> {
            zeroFavorite.setZeroProduct(zeroProductService.getById(zeroFavorite.getProductId()));
        });
        return zeroFavoriteList;
    }
}
