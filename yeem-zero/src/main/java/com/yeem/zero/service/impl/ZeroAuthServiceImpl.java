package com.yeem.zero.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.common.dto.WechatMiniProgramDTO;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.service.IZeroAuthService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

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
        String loginUrl = environment.getProperty("auth.wechat-mini-program");
        if (StringUtils.isEmpty(loginUrl)) {
            throw new RuntimeException("login url is null");
        }
        HttpResponse httpResponse = null;
        try {
            wechatMiniProgramDTO.setApplication("zero");
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
        log.info("{}", wechatMiniProgramDTO);
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(wechatMiniProgramDTO.getUsername());
        if (StringUtils.isEmpty(zeroUserExtra)) {
            zeroUserExtra = new ZeroUserExtra();
            zeroUserExtra.setUserId(wechatMiniProgramDTO.getUserId());
            zeroUserExtra.setUsername(wechatMiniProgramDTO.getUsername());
            zeroUserExtra.setNickName(wechatMiniProgramDTO.getNickName());
            zeroUserExtra.setAvatarUrl(wechatMiniProgramDTO.getAvatarUrl());
            zeroUserExtra.setGender(wechatMiniProgramDTO.getGender());
            zeroUserExtra.setPhoneNumber(wechatMiniProgramDTO.getPhoneNumber());
            zeroUserExtraService.save(zeroUserExtra);
        }
        return wechatMiniProgramDTO.getResponse();
    }
}
