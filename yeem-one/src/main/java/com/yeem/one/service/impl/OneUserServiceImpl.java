package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.entity.OneAddress;
import com.yeem.one.entity.OneCart;
import com.yeem.one.entity.OneUser;
import com.yeem.one.mapper.OneUserMapper;
import com.yeem.one.service.IOneAddressService;
import com.yeem.one.service.IOneCartService;
import com.yeem.one.service.IOneUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OneUserServiceImpl extends ServiceImpl<OneUserMapper, OneUser> implements IOneUserService {

    @Autowired
    private IOneAddressService oneAddressService;
    @Autowired
    private IOneCartService oneCartService;

    @Override
    public OneUser getByIdWithOther(Long id) {
        // 查询用户信息
        OneUser user = super.getById(id);
        // 查询地址列表
        List<OneAddress> addressList = oneAddressService.listByUserId(id);
        user.setAddressList(addressList);
        // 查询购物车列表
        List<OneCart> cartList = oneCartService.listByUserId(id);
        user.setCartList(cartList);
        return user;
    }
}
