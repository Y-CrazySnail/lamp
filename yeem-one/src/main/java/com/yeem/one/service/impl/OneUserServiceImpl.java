package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.entity.OneAddress;
import com.yeem.one.entity.OneUser;
import com.yeem.one.mapper.OneUserMapper;
import com.yeem.one.service.IOneAddressService;
import com.yeem.one.service.IOneUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Service
public class OneUserServiceImpl extends ServiceImpl<OneUserMapper, OneUser> implements IOneUserService {

    @Autowired
    private IOneAddressService oneAddressService;

    @Override
    public OneUser getByIdWithAddress(Long id) {
        OneUser user = super.getById(id);
        List<OneAddress> addressList = oneAddressService.listByUserId(id);
        user.setAddressList(addressList);
        return user;
    }
}
