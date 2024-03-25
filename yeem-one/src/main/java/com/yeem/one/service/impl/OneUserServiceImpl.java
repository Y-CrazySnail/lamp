package com.yeem.one.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.dto.WxLoginResponse;
import com.yeem.common.utils.WechatJWTUtils;
import com.yeem.common.utils.WechatUtils;
import com.yeem.one.entity.OneAddress;
import com.yeem.one.entity.OneCart;
import com.yeem.one.entity.OneTenant;
import com.yeem.one.entity.OneUser;
import com.yeem.one.mapper.OneUserMapper;
import com.yeem.one.service.IOneAddressService;
import com.yeem.one.service.IOneCartService;
import com.yeem.one.service.IOneTenantService;
import com.yeem.one.service.IOneUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class OneUserServiceImpl extends ServiceImpl<OneUserMapper, OneUser> implements IOneUserService {

    @Autowired
    private IOneTenantService oneTenantService;
    @Autowired
    private IOneAddressService oneAddressService;
    @Autowired
    private IOneCartService oneCartService;
    @Autowired
    private OneUserMapper mapper;

    @Override
    public <E extends IPage<OneUser>> E page(E page, Wrapper<OneUser> queryWrapper) {
        page = super.page(page, queryWrapper);
        for (OneUser user : page.getRecords()) {
            List<OneAddress> addressList = oneAddressService.listByUserId(user.getId());
            user.setAddressList(addressList);
            user.setUserAddressCount(addressList.size());
        }
        return page;
    }

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

    @Override
    public OneUser getByWechatOpenId(String wechatOpenId) {
        LambdaQueryWrapper<OneUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneUser::getDeleteFlag, false);
        queryWrapper.eq(OneUser::getWechatOpenId, wechatOpenId);
        return super.getOne(queryWrapper);
    }

    @Override
    public OneUser login(OneUser user) {
        String wechatAppId = Base64.decodeStr(user.getWechatAppId());
        OneTenant tenant = oneTenantService.getByWechatAppId(wechatAppId);
        if (null != tenant) {
            user.setTenantId(tenant.getId());
        }
        String wechatOpenId;
        String sessionKey;
        WxLoginResponse wxLoginResponse = null;
        try {
            wxLoginResponse = WechatUtils.getWxLoginResponse(tenant.getWechatAppId(), tenant.getWechatAppSecret(), user.getWechatCode());
        } catch (IOException e) {
            log.error("wechat login error", e);
        }
        log.info("wechat login api response:{}", wxLoginResponse.getOpenid());
        wechatOpenId = wxLoginResponse.getOpenid();
        sessionKey = wxLoginResponse.getSession_key();
        log.info("openId:{}", wechatOpenId);
        log.info("sessionKey:{}", sessionKey);
        OneUser checkOneUser = this.getByWechatOpenId(wechatOpenId);
        if (null == checkOneUser || null == checkOneUser.getId()) {
            user.setWechatOpenId(wechatOpenId);
            mapper.insert(user);
        }
        OneUser resOneUser = this.getByWechatOpenId(wechatOpenId);
        String token = WechatJWTUtils.generateJWT(String.valueOf(tenant.getId()), resOneUser.getId(), resOneUser.getWechatOpenId());
        resOneUser.setToken(token);
        return resOneUser;
    }
}
