package com.yeem.zero.controller.web;

import com.yeem.log.OperateLog;
import com.yeem.zero.entity.ZeroBalanceRecord;
import com.yeem.zero.service.IZeroBalanceRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理端-余额记录
 */
@Slf4j
@RestController
@RequestMapping("/manager-zero-balance-record")
public class ZeroMBalanceRecordController {

    @Autowired
    private IZeroBalanceRecordService zeroBalanceRecordService;

}
