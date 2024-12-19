package com.lamp.controller.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampInbound;
import com.lamp.service.manage.MLampInboundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage/lamp-inbound")
public class MLampInboundController {

    @Autowired
    private MLampInboundService inboundService;

    @GetMapping("/page")
    public ResponseEntity<Object> page(LampInbound inbound) {
        try {
            IPage<LampInbound> page = new Page<>();
            LambdaQueryWrapper<LampInbound> queryWrapper = new LambdaQueryWrapper<>(LampInbound.class);
            BaseEntity.setDeleteFlagCondition(queryWrapper);
            return ResponseEntity.ok(inboundService.page(page, queryWrapper));
        } catch (Exception e) {
            log.error("获取入站列表失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取入站列表失败");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam(value = "id") Long id) {
        try {
            return ResponseEntity.ok(inboundService.getById(id));
        } catch (Exception e) {
            log.error("获取入站详情失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取入站详情失败");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody LampInbound inbound) {
        try {
            inboundService.save(inbound);
            return ResponseEntity.ok("入站添加信息成功");
        } catch (Exception e) {
            log.error("添加入站信息失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加入站信息失败");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> remove(@RequestBody LampInbound inbound) {
        try {
            inboundService.removeById(inbound.getId());
            return ResponseEntity.ok("入站删除成功");
        } catch (Exception e) {
            log.error("入站删除失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("入站删除失败");
        }
    }
}
