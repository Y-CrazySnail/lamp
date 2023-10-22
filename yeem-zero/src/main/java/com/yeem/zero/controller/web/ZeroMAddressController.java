package com.yeem.zero.controller.web;

import com.yeem.log.OperateLog;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.entity.ZeroAddress;
import com.yeem.zero.service.IZeroAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端-地址信息
 */
@Slf4j
@RestController
@RequestMapping("/manager-zero-address")
public class ZeroMAddressController {

    @Autowired
    private IZeroAddressService zeroAddressService;

    @Autowired
    private Environment environment;

}
