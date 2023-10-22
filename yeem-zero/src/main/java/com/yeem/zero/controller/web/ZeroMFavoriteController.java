package com.yeem.zero.controller.web;

import com.yeem.log.OperateLog;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.entity.ZeroFavorite;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.security.WechatAuthInterceptor;
import com.yeem.zero.service.IZeroFavoriteService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端-收藏信息
 */
@Slf4j
@RestController
@RequestMapping("/manager-zero-favorite")
public class ZeroMFavoriteController {

    @Autowired
    private IZeroFavoriteService zeroFavoriteService;

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

}
