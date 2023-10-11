package com.yeem.zero.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.common.dto.WechatMiniProgramDTO;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.service.IZeroAuthService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@Service
public class ZeroAuthServiceImpl implements IZeroAuthService {

    @Autowired
    private Environment environment;
    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    @Override
    public String wechatMiniProgramLogin(WechatMiniProgramDTO wechatMiniProgramDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        String loginUrl = environment.getProperty("wechat.login-url");
        if (StringUtils.isEmpty(loginUrl)) {
            throw new RuntimeException("login url is null");
        }
        HttpResponse httpResponse = null;
        try {
            wechatMiniProgramDTO.setApplication(environment.getProperty("wechat.active"));
            httpResponse = HttpRequest.post(loginUrl).body(objectMapper.writeValueAsString(wechatMiniProgramDTO)).execute();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("{}", httpResponse);
        if (null == httpResponse || HttpStatus.OK.value() != httpResponse.getStatus()) {
            throw new RuntimeException("");
        }
        try {
            wechatMiniProgramDTO = objectMapper.readValue(
                    httpResponse.body(),
                    WechatMiniProgramDTO.class
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("wechat mini program login info：{}", wechatMiniProgramDTO);
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(wechatMiniProgramDTO.getUsername());
        if (StringUtils.isEmpty(zeroUserExtra)) {
            zeroUserExtra = new ZeroUserExtra();
            zeroUserExtra.setUserId(wechatMiniProgramDTO.getUserId());
            zeroUserExtra.setUsername(wechatMiniProgramDTO.getUsername());
            if (!StringUtils.isEmpty(wechatMiniProgramDTO.getReferrerUsername())) {
                zeroUserExtra.setDirectReferrerUsername(wechatMiniProgramDTO.getReferrerUsername());
                log.info("direct referrer username:{}", wechatMiniProgramDTO.getReferrerUsername());
                ZeroUserExtra directReferrerUserExtra = zeroUserExtraService.get(wechatMiniProgramDTO.getReferrerUsername());
                if (!StringUtils.isEmpty(directReferrerUserExtra)
                        && !StringUtils.isEmpty(directReferrerUserExtra.getDirectReferrerUsername())) {
                    log.info("indirect referrer username:{}", wechatMiniProgramDTO.getUsername());
                    zeroUserExtra.setIndirectReferrerUsername(directReferrerUserExtra.getDirectReferrerUsername());
                }
            }
            zeroUserExtra.setNickName(wechatMiniProgramDTO.getNickName());
            zeroUserExtra.setAvatarUrl(wechatMiniProgramDTO.getAvatarUrl());
            zeroUserExtra.setGender(wechatMiniProgramDTO.getGender());
            zeroUserExtra.setPhoneNumber(wechatMiniProgramDTO.getPhoneNumber());
            zeroUserExtra.setWechatOpenId(wechatMiniProgramDTO.getWechatOpenId());
            zeroUserExtraService.save(zeroUserExtra);
        }
        return wechatMiniProgramDTO.getResponse();
    }
}
