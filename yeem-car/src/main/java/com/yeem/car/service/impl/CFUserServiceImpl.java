package com.yeem.car.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.CFUser;
import com.yeem.car.entity.CarFilmQuality;
import com.yeem.car.entity.CarFilmUser;
import com.yeem.car.mapper.CFUserMapper;
import com.yeem.car.service.ICFUserService;
import com.yeem.common.dto.WxLoginResponse;
import com.yeem.common.utils.WechatJWTUtils;
import com.yeem.common.utils.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class CFUserServiceImpl extends ServiceImpl<CFUserMapper, CFUser> implements ICFUserService {


    @Value("${wechat.wz0005.app-id}")
    private String WECHAT_WZ0005_APP_ID;
    @Value("${wechat.wz0005.app-secret}")
    private String WECHAT_WZ0005_APP_SECRET;
    @Value("${wechat.al0001.app-id}")
    private String WECHAT_AL0001_APP_ID;
    @Value("${wechat.al0001.app-secret}")
    private String WECHAT_AL0001_APP_SECRET;
    @Value("${wechat.yxbb02.app-id}")
    private String WECHAT_YXBB02_APP_ID;
    @Value("${wechat.yxbb02.app-secret}")
    private String WECHAT_YXBB02_APP_SECRET;
    @Value("${wechat.xpxl03.app-id}")
    private String WECHAT_XPXL03_APP_ID;
    @Value("${wechat.xpxl03.app-secret}")
    private String WECHAT_XPXL03_APP_SECRET;
    @Value("${wechat.tk0004.app-id}")
    private String WECHAT_TK0004_APP_ID;
    @Value("${wechat.tk0004.app-secret}")
    private String WECHAT_TK0004_APP_SECRET;


    @Autowired
    private CFUserMapper userMapper;

    @Override
    public CFUser login(CFUser user) {
        String appId = calculateAppId(user.getTenantNo());
        String appSecret = calculateAppSecret(user.getTenantNo());
        String openId;
        WxLoginResponse wxLoginResponse = null;
        try {
            wxLoginResponse = WechatUtils.wechatLogin(appId, appSecret, user.getCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null == wxLoginResponse || StrUtil.isEmpty(wxLoginResponse.getOpenid())) {
            throw new RuntimeException("小程序认证失败");
        }
        openId = wxLoginResponse.getOpenid();
        log.info("openId:{}", openId);
//        CarFilmUser checkZeroUserExtra = this.get(carFilmUser.getProductNo(), openId);
//        if (null == checkZeroUserExtra || null == checkZeroUserExtra.getId()) {
//            carFilmUser.setOpenId(openId);
//            this.save(carFilmUser);
//        }
//        carFilmUser = this.get(carFilmUser.getProductNo(), openId);
//        if (null == carFilmUser) {
//            throw new RuntimeException("小程序认证失败");
//        }
//        // 在保 过期数量设置
//        if (StrUtil.isEmpty(carFilmUser.getPhone())) {
//            carFilmUser.setNormalQualityNumber(0);
//            carFilmUser.setExpiredQualityNumber(0);
//        } else {
//            carFilmUser.setNormalQualityNumber(0);
//            carFilmUser.setExpiredQualityNumber(0);
//            List<CarFilmQuality> carFilmQualityList = carFilmQualityService.getQualityInfo(carFilmUser.getProductNo(), carFilmUser.getPhone());
//            for (CarFilmQuality carFilmQuality : carFilmQualityList) {
//                carFilmQuality.setState();
//                if (CarFilmQuality.State.NORMAL.getValue().equals(carFilmQuality.getState())) {
//                    carFilmUser.setNormalQualityNumber(carFilmUser.getNormalQualityNumber() + 1);
//                } else {
//                    carFilmUser.setExpiredQualityNumber(carFilmUser.getExpiredQualityNumber() + 1);
//                }
//            }
//        }
//        String token = WechatJWTUtils.generateJWT(carFilmUser.getProductNo(), carFilmUser.getId(), carFilmUser.getOpenId());
//        carFilmUser.setToken(token);
//        return carFilmUser;
        return null;
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
        } else if ("AL0001".equals(productNo)) {
            return WECHAT_AL0001_APP_ID;
        } else if ("YXBB02".equals(productNo)) {
            return WECHAT_YXBB02_APP_ID;
        } else if ("XPXL03".equals(productNo)) {
            return WECHAT_XPXL03_APP_ID;
        } else if ("TK0004".equals(productNo)) {
            return WECHAT_TK0004_APP_ID;
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
        } else if ("AL0001".equals(productNo)) {
            return WECHAT_AL0001_APP_SECRET;
        } else if ("YXBB02".equals(productNo)) {
            return WECHAT_YXBB02_APP_SECRET;
        } else if ("XPXL03".equals(productNo)) {
            return WECHAT_XPXL03_APP_SECRET;
        } else if ("TK0004".equals(productNo)) {
            return WECHAT_TK0004_APP_SECRET;
        }
        return null;
    }
}
