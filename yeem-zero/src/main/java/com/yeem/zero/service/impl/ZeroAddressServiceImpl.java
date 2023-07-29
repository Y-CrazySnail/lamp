package com.yeem.zero.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.utils.OauthUtils;
import com.yeem.zero.entity.ZeroAddress;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.mapper.ZeroAddressMapper;
import com.yeem.zero.service.IZeroAddressService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class ZeroAddressServiceImpl extends ServiceImpl<ZeroAddressMapper, ZeroAddress> implements IZeroAddressService {

    @Autowired
    private ZeroAddressMapper zeroAddressMapper;

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    @Override
    public List<ZeroAddress> listByUsername(String username) {
        return zeroAddressMapper.listByUsername(username);
    }

    @DS("zero")
    @Override
    @Transactional
    public boolean save(ZeroAddress zeroAddress) {
        String username = OauthUtils.getUsername();
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(username);
        if (StringUtils.isEmpty(zeroUserExtra)) {
            throw new RuntimeException("user info is null when save address");
        }
        zeroAddress.setUserId(zeroUserExtra.getUserId());
        QueryWrapper<ZeroAddress> zeroAddressQueryWrapper = new QueryWrapper<>();
        zeroAddressQueryWrapper.eq("user_id", zeroUserExtra.getUserId());
        zeroAddressQueryWrapper.eq("default_flag", 1);
        int count = zeroAddressMapper.selectCount(zeroAddressQueryWrapper);
        if (count == 0) {
            zeroAddress.setDefaultFlag(1);
        }
        return SqlHelper.retBool(zeroAddressMapper.insert(zeroAddress));
    }

    @DS("zero")
    @Override
    @Transactional
    public boolean update(ZeroAddress zeroAddress) {
        if (Objects.equals(zeroAddress.getDefaultFlag(), 1)) {
            UpdateWrapper<ZeroAddress> zeroAddressUpdateWrapper = new UpdateWrapper<>();
            zeroAddressUpdateWrapper.eq("user_id", zeroAddress.getUserId());
            zeroAddressUpdateWrapper.ne("id", zeroAddress.getId());
            zeroAddressUpdateWrapper.set("default_flag", 0);
            zeroAddressMapper.update(null, zeroAddressUpdateWrapper);
        }
        return SqlHelper.retBool(zeroAddressMapper.updateById(zeroAddress));
    }

    @DS("zero")
    @Override
    @Transactional
    public boolean removeById(ZeroAddress zeroAddress) {
        zeroAddress.setDeleteFlag(true);
        return SqlHelper.retBool(zeroAddressMapper.updateById(zeroAddress));
    }
}
