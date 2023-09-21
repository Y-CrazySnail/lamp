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

    /**
     * 查询用户余额变动明细列表
     *
     * @return 余额变动明细列表
     * @apiNote 根据用户名查询用户余额变动明细列表
     */
    @OperateLog(operateModule = "余额明细模块", operateType = "查询用户余额变动明细列表", operateDesc = "查询用户余额变动明细列表")
    @GetMapping("list")
    public ResponseEntity<List<ZeroBalanceRecord>> list() {
        try {
            List<ZeroBalanceRecord> zeroBalanceRecordList = zeroBalanceRecordService.listByUsername();
            return ResponseEntity.ok(zeroBalanceRecordList);
        } catch (Exception e) {
            log.error("list balance record error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
