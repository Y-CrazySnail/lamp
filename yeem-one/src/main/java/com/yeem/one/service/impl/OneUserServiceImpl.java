package com.yeem.one.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.dto.WxLoginResponse;
import com.yeem.one.util.OneWechatJWTUtils;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class OneUserServiceImpl extends ServiceImpl<OneUserMapper, OneUser> implements IOneUserService {

    @Autowired
    private IOneTenantService tenantService;
    @Autowired
    private IOneAddressService addressService;
    @Autowired
    private IOneCartService cartService;
    @Autowired
    private OneUserMapper mapper;

    @Override
    public <E extends IPage<OneUser>> E page(E page, Wrapper<OneUser> queryWrapper) {
        page = super.page(page, queryWrapper);
        for (OneUser user : page.getRecords()) {
            List<OneAddress> addressList = addressService.listByUserId(user.getId());
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
        List<OneAddress> addressList = addressService.listByUserId(id);
        user.setAddressList(addressList);
        // 查询购物车列表
        List<OneCart> cartList = cartService.listByUserId(id);
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
        OneTenant tenant = tenantService.getByWechatAppId(wechatAppId);
        if (null == tenant || StrUtil.isEmpty(tenant.getWechatAppId())) {
            throw new RuntimeException("tenant info error");
        }
        user.setTenantId(tenant.getId());
        String wechatOpenId;
        String sessionKey;
        WxLoginResponse wxLoginResponse = null;
        try {
            wxLoginResponse = WechatUtils.wechatLogin(tenant.getWechatAppId(), tenant.getWechatAppSecret(), user.getWechatCode());
        } catch (IOException e) {
            log.error("wechat login error", e);
        }
        if (null == wxLoginResponse || StrUtil.isEmpty(wxLoginResponse.getOpenid())) {
            log.error("wechat login error");
            throw new RuntimeException("wechat login error");
        }
        wechatOpenId = wxLoginResponse.getOpenid();
        sessionKey = wxLoginResponse.getSession_key();
        log.info("wechat login openId:{}", wechatOpenId);
        log.info("wechat login sessionKey:{}", sessionKey);
        OneUser checkOneUser = this.getByWechatOpenId(wechatOpenId);
        if (null == checkOneUser || null == checkOneUser.getId()) {
            log.info("the user has not registered before:{}", user);
            user.setWechatOpenId(wechatOpenId);
            mapper.insert(user);
        }
        OneUser resOneUser = this.getByWechatOpenId(wechatOpenId);
        String token = OneWechatJWTUtils.generateJWT(tenant.getId(), resOneUser.getId(), resOneUser.getWechatOpenId());
        resOneUser.setToken(token);
        return resOneUser;
    }
}
