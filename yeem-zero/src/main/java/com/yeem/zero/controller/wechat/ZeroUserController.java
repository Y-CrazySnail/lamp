package com.yeem.zero.controller.wechat;

import com.yeem.common.dto.WxLoginResponse;
import com.yeem.common.utils.WechatJWTUtils;
import com.yeem.common.utils.WechatUtils;
import com.yeem.log.OperateLog;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.security.WechatAuthInterceptor;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 微信小程序-用户信息
 */
@Slf4j
@RestController
@RequestMapping("/wechat/zero-user")
public class ZeroUserController {

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    @Autowired
    private Environment environment;

    /**
     * 小程序登录
     *
     * @return 登录信息
     * @apiNote 小程序登录
     */
    @PostMapping("login")
    public ResponseEntity<ZeroUserExtra> login(@RequestBody ZeroUserExtra zeroUserExtra) {
        String active = environment.getProperty("wechat.active");
        String appId = environment.getProperty("wechat." + active + ".app-id");
        String appSecret = environment.getProperty("wechat." + active + ".app-secret");
        String openId;
        String sessionKey;
        try {
            WxLoginResponse wxLoginResponse = WechatUtils.getWxLoginResponse(appId, appSecret, zeroUserExtra.getWechatLoginCode());
            log.info("wx login api response:{}", wxLoginResponse.getOpenid());
            openId = wxLoginResponse.getOpenid();
            sessionKey = wxLoginResponse.getSession_key();
            log.info("openId:{}", openId);
            log.info("sessionKey:{}", sessionKey);
            ZeroUserExtra checkZeroUserExtra = zeroUserExtraService.getByWechatOpenId(openId);
            if (StringUtils.isEmpty(checkZeroUserExtra) || StringUtils.isEmpty(checkZeroUserExtra.getId())) {
                zeroUserExtra.setWechatOpenId(openId);
                zeroUserExtraService.save(zeroUserExtra);
            }
            ZeroUserExtra resZeroUserExtra = zeroUserExtraService.getByWechatOpenId(openId);
            resZeroUserExtra.setSessionKey(sessionKey);
            if (!StringUtils.isEmpty(resZeroUserExtra.getPhoneNumber())) {
                String token = WechatJWTUtils.generateJWT(active, resZeroUserExtra.getId(), resZeroUserExtra.getWechatOpenId());
                resZeroUserExtra.setToken(token);
            }
            return ResponseEntity.ok(resZeroUserExtra);
        } catch (IOException e) {
            log.error("wx login api error：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 小程序手机号解析
     *
     * @return 用户信息
     * @apiNote 小程序手机号解析
     */
    @PostMapping("phone")
    public ResponseEntity<ZeroUserExtra> phone(@RequestBody ZeroUserExtra zeroUserExtra) {
        String active = environment.getProperty("wechat.active");
        String phoneNumber = null;
        try {
            phoneNumber = WechatUtils.decryptPhone(
                    zeroUserExtra.getSessionKey(),
                    zeroUserExtra.getEncryptedData(),
                    zeroUserExtra.getIv());
        } catch (Exception e) {
            log.error("decryption phone number error:", e);
        }
        if (StringUtils.isEmpty(phoneNumber)) {
            throw new RuntimeException("decryption phone number error");
        }
        ZeroUserExtra zeroUserExtraSet = zeroUserExtraService.getByWechatOpenId(zeroUserExtra.getWechatOpenId());
        zeroUserExtra.setId(zeroUserExtraSet.getId());
        zeroUserExtra.setPhoneNumber(phoneNumber);
        zeroUserExtraService.updateById(zeroUserExtra);
        ZeroUserExtra zeroUserExtraRes = zeroUserExtraService.getById(zeroUserExtraSet.getId());
        String token = WechatJWTUtils.generateJWT(active, zeroUserExtraRes.getId(), zeroUserExtraRes.getWechatOpenId());
        zeroUserExtraRes.setToken(token);
        return ResponseEntity.ok(zeroUserExtraRes);
    }

    /**
     * 查询用户信息
     *
     * @return 用户信息
     * @apiNote 查询用户信息
     */
    @OperateLog(operateModule = "用户模块", operateType = "查询用户信息", operateDesc = "查询用户信息")
    @GetMapping("get")
    public ResponseEntity<ZeroUserExtra> get() {
        Long userId = WechatAuthInterceptor.getUserId();
        ZeroUserExtra userExtra;
        try {
            userExtra = zeroUserExtraService.get(userId);
        } catch (Exception e) {
            log.error("get user extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(userExtra);
    }

    /**
     * 更新用户信息
     *
     * @return 用户信息
     * @apiNote 更新用户信息
     */
    @OperateLog(operateModule = "用户模块", operateType = "更新用户信息", operateDesc = "更新用户信息")
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody ZeroUserExtra entity) {
        try {
            zeroUserExtraService.updateById(entity);
        } catch (Exception e) {
            log.error("update user extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(entity);
    }

    /**
     * 分销团队查询
     *
     * @param nickName 昵称（模糊查询）
     * @return 分销团队用户信息
     * @apiNote 分销团队查询
     */
    @OperateLog(operateModule = "用户模块", operateType = "分销团队查询", operateDesc = "分销团队查询")
    @GetMapping("distribution")
    public ResponseEntity<List<ZeroUserExtra>> distribution(@RequestParam("nickName") String nickName) {
        Long userId = WechatAuthInterceptor.getUserId();
        List<ZeroUserExtra> zeroUserExtraList;
        try {
            zeroUserExtraList = zeroUserExtraService.distribution(userId, nickName);
        } catch (Exception e) {
            log.error("update user extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(zeroUserExtraList);
    }
}
