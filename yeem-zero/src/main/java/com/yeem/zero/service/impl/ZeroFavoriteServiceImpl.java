package com.yeem.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.config.Constant;
import com.yeem.zero.entity.ZeroCart;
import com.yeem.zero.entity.ZeroFavorite;
import com.yeem.zero.entity.ZeroProduct;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.mapper.ZeroFavoriteMapper;
import com.yeem.zero.service.IZeroFavoriteService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class ZeroFavoriteServiceImpl extends ServiceImpl<ZeroFavoriteMapper, ZeroFavorite> implements IZeroFavoriteService {

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    @Override
    public List<ZeroFavorite> listByUsername(String username) {
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(username);
        if (StringUtils.isEmpty(zeroUserExtra)) {
            throw new RuntimeException("user info is null when save favorite");
        }
        QueryWrapper<ZeroFavorite> zeroFavoriteQueryWrapper = new QueryWrapper<>();
        zeroFavoriteQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
        zeroFavoriteQueryWrapper.eq("user_id", zeroUserExtra.getUserId());
        return baseMapper.selectList(zeroFavoriteQueryWrapper);
    }
}
