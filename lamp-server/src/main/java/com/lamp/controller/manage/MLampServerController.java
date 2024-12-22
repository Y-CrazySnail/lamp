package com.lamp.controller.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampServer;
import com.lamp.service.manage.MLampServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage/lamp-server")
public class MLampServerController {

    @Autowired
    private MLampServerService serverService;

    @GetMapping("/page")
    public ResponseEntity<Object> page() {
        try {
            IPage<LampServer> page = new Page<>();
            LambdaQueryWrapper<LampServer> queryWrapper = new LambdaQueryWrapper<>(LampServer.class);
            BaseEntity.setDeleteFlagCondition(queryWrapper);
            return ResponseEntity.ok(serverService.page(page, queryWrapper));
        } catch (Exception e) {
            log.error("获取服务器列表失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取服务器列表失败");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam(value = "id") Long id) {
        try {
            return ResponseEntity.ok(serverService.getById(id));
        } catch (Exception e) {
            log.error("获取服务器详情失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取服务器详情失败");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody LampServer server) {
        try {
            serverService.save(server);
            return ResponseEntity.ok("添加服务器成功");
        } catch (Exception e) {
            log.error("添加服务器失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加服务器失败");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody LampServer server) {
        try {
            serverService.updateById(server);
            return ResponseEntity.ok("服务器信息更新成功");
        } catch (Exception e) {
            log.error("更新服务器信息失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新服务器信息失败");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> remove(@RequestBody LampServer server) {
        try {
            serverService.removeById(server.getId());
            return ResponseEntity.ok("服务器删除成功");
        } catch (Exception e) {
            log.error("服务器删除失败:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器删除失败");
        }
    }

    @PostMapping("/sync")
    public ResponseEntity<Object> sync(@RequestBody LampServer server) {
        try {
            serverService.sync(server, null);
            return ResponseEntity.ok("服务器同步成功");
        } catch (Exception e) {
            log.error("服务器同步失败:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器同步失败");
        }
    }
}
