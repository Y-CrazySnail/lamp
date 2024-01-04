package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.CarFilmTenant;
import com.yeem.car.entity.CarFilmUser;
import com.yeem.car.mapper.CarFilmUserMapper;
import com.yeem.car.service.ICarFilmTenantService;
import com.yeem.car.service.ICarFilmUserService;
import com.yeem.common.dto.WxLoginResponse;
import com.yeem.common.utils.WechatJWTUtils;
import com.yeem.common.utils.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CarFilmUserServiceImpl extends ServiceImpl<CarFilmUserMapper, CarFilmUser> implements ICarFilmUserService {

    @Autowired
    private ICarFilmTenantService carFilmTenantService;
    @Autowired
    private CarFilmUserMapper carFilmUserMapper;

    @Value("${wechat.wz0005.app-id}")
    private String WECHAT_WZ0005_APP_ID;
    @Value("${wechat.wz0005.app-secret}")
    private String WECHAT_WZ0005_APP_SECRET;

    @Override
    public IPage<CarFilmUser> pages(int current, int size, String productNo, String nickName, String phone) {
        Page<CarFilmUser> page = new Page<>(current, size);
        QueryWrapper<CarFilmUser> wrapper = new QueryWrapper<>();
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        wrapper.in("product_no", carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()));
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.like("phone", phone);
        }
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.like("nick_name", nickName);
        }
        wrapper.eq("delete_flag", 0);
        return carFilmUserMapper.selectPage(page, wrapper);
    }

    @Override
    public CarFilmUser get(String productNo, String openId) {
        QueryWrapper<CarFilmUser> carFilmUserQueryWrapper = new QueryWrapper<>();
        carFilmUserQueryWrapper.eq("product_no", productNo);
        carFilmUserQueryWrapper.eq("open_id", openId);
        return carFilmUserMapper.selectOne(carFilmUserQueryWrapper);
    }

    @Override
    public CarFilmUser login(CarFilmUser carFilmUser) {
        String appId = calculateAppId(carFilmUser.getProductNo());
        String appSecret = calculateAppSecret(carFilmUser.getProductNo());
        String openId;
        WxLoginResponse wxLoginResponse = null;
        try {
            wxLoginResponse = WechatUtils.getWxLoginResponse(appId, appSecret, carFilmUser.getCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(wxLoginResponse)) {
            throw new RuntimeException("小程序认证失败");
        }
        openId = wxLoginResponse.getOpenid();
        log.info("openId:{}", openId);
        CarFilmUser checkZeroUserExtra = this.get(carFilmUser.getProductNo(), openId);
        if (StringUtils.isEmpty(checkZeroUserExtra) || StringUtils.isEmpty(checkZeroUserExtra.getId())) {
            carFilmUser.setOpenId(openId);
            this.save(carFilmUser);
        }
        String token = WechatJWTUtils.generateJWT(carFilmUser.getProductNo(), carFilmUser.getId(), carFilmUser.getOpenId());
        carFilmUser.setToken(token);
        return carFilmUser;
    }

    /**
     * 解析当前产品AppId
     *
     * @param productNo 产品代码
     * @return AppId
     */
    private String calculateAppId(String productNo) {
        if ("WZ0005".equals(productNo)) {
            return WECHAT_WZ0005_APP_ID;
        }
        return null;
    }

    /**
     * 解析当前产品AppSecret
     *
     * @param productNo 产品代码
     * @return AppSecret
     */
    private String calculateAppSecret(String productNo) {
        if ("WZ0005".equals(productNo)) {
            return WECHAT_WZ0005_APP_SECRET;
        }
        return null;
    }
}
