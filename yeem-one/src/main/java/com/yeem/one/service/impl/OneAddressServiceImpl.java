package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneAddress;
import com.yeem.one.mapper.OneAddressMapper;
import com.yeem.one.service.IOneAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OneAddressServiceImpl extends ServiceImpl<OneAddressMapper, OneAddress> implements IOneAddressService {

    @Autowired
    private OneAddressMapper mapper;

    @Override
    public List<OneAddress> listByUserId(Long userId) {
        LambdaQueryWrapper<OneAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneAddress::getDeleteFlag, Constant.BOOLEAN_FALSE);
        queryWrapper.eq(OneAddress::getUserId, userId);
        return mapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<OneAddress> saveForWechat(OneAddress address) {
        if (address.getAddressDefaultFlag()) {
            // 当前用户所有地址改为非默认
            LambdaUpdateWrapper<OneAddress> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(OneAddress::getAddressDefaultFlag, false);
            updateWrapper.eq(OneAddress::getUserId, address.getUserId());
            mapper.update(null, updateWrapper);
        }
        mapper.insert(address);
        List<OneAddress> addressList = listByUserId(address.getUserId());
        long defaultCount = addressList.stream().filter(OneAddress::getAddressDefaultFlag).count();
        if (defaultCount == 0) {
            // 当前用户没有默认地址，设置第一个地址为默认地址
            addressList.get(0).setAddressDefaultFlag(true);
            mapper.updateById(addressList.get(0));
        }
        return addressList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<OneAddress> updateForWechat(OneAddress address) {
        if (address.getAddressDefaultFlag()) {
            // 当前用户所有地址改为非默认
            LambdaUpdateWrapper<OneAddress> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(OneAddress::getAddressDefaultFlag, false);
            updateWrapper.eq(OneAddress::getUserId, address.getUserId());
            mapper.update(null, updateWrapper);
        }
        mapper.updateById(address);
        List<OneAddress> addressList = listByUserId(address.getUserId());
        long defaultCount = addressList.stream().filter(OneAddress::getAddressDefaultFlag).count();
        if (defaultCount == 0) {
            // 当前用户没有默认地址，设置第一个地址为默认地址
            addressList.get(0).setAddressDefaultFlag(true);
            mapper.updateById(addressList.get(0));
        }
        return addressList;
    }

    @Override
    public List<OneAddress> removeForWechat(OneAddress address) {
        LambdaUpdateWrapper<OneAddress> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(OneAddress::getDeleteFlag, true);
        updateWrapper.eq(OneAddress::getTenantId, address.getTenantId());
        updateWrapper.eq(OneAddress::getUserId, address.getUserId());
        updateWrapper.eq(OneAddress::getId, address.getId());
        mapper.update(null, updateWrapper);
        List<OneAddress> addressList = listByUserId(address.getUserId());
        if (!addressList.isEmpty()) {
            long defaultCount = addressList.stream().filter(OneAddress::getAddressDefaultFlag).count();
            if (defaultCount == 0) {
                // 当前用户没有默认地址，设置第一个地址为默认地址
                addressList.get(0).setAddressDefaultFlag(true);
                mapper.updateById(addressList.get(0));
            }
        }
        return addressList;
    }
}
