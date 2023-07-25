package com.yeem.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.entity.BaseEntity;
import com.yeem.utils.OauthUtils;
import com.yeem.zero.entity.ZeroCart;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.mapper.ZeroCartMapper;
import com.yeem.zero.service.IZeroCartService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Slf4j
@Service
public class ZeroCartServiceImpl extends ServiceImpl<ZeroCartMapper, ZeroCart> implements IZeroCartService {

    @Autowired
    private ZeroCartMapper zeroCartMapper;

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    @Override
    public boolean save(ZeroCart zeroCart) {
        String username = OauthUtils.getUsername();
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(username);
        if (StringUtils.isEmpty(zeroUserExtra)) {
            throw new RuntimeException("user info is null when save cart");
        }
        zeroCart.setUserId(zeroUserExtra.getUserId());
        return super.save(zeroCart);
    }

    @Override
    public List<ZeroCart> listByUsername() {
        String username = OauthUtils.getUsername();
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(username);
        if (StringUtils.isEmpty(zeroUserExtra)) {
            throw new RuntimeException("user info is null when save cart");
        }
        QueryWrapper<ZeroCart> zeroCartQueryWrapper = new QueryWrapper<>();
        zeroCartQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), "0");
        zeroCartQueryWrapper.eq("user_id", zeroUserExtra.getUserId());
        return zeroCartMapper.selectList(zeroCartQueryWrapper);
    }

    @Override
    public boolean remove(ZeroCart zeroCart) {
        zeroCart.setDeleteFlag(true);
        return SqlHelper.retBool(zeroCartMapper.updateById(zeroCart));
    }
}
