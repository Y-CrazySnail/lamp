package com.yeem.zero.controller.web;

import com.yeem.log.OperateLog;
import com.yeem.zero.entity.ZeroCart;
import com.yeem.zero.service.IZeroCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端-购物车信息
 */
@Slf4j
@RestController
@RequestMapping("/manager-zero-cart")
public class ZeroMCartController {

    @Autowired
    private IZeroCartService zeroCartService;

}
