package com.yeem.his.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.his.entity.HisService;
import com.yeem.his.service.IHisServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/his/service")
public class HisServiceController {

    @Autowired
    private IHisServiceService serviceService;

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(serviceService.list());
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Object> page(@RequestParam(value = "serviceName", required = false) String serviceName) {
        try {
            IPage<HisService> page = new Page<>();
            LambdaQueryWrapper<HisService> queryWrapper = new LambdaQueryWrapper<>();
            if (StrUtil.isNotEmpty(serviceName)) {
                queryWrapper.like(HisService::getServiceName, serviceName);
            }
            return ResponseEntity.ok(serviceService.page(page, queryWrapper));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(serviceService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody HisService service) {
        try {
            serviceService.save(service);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody HisService service) {
        try {
            serviceService.updateById(service);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> remove(@RequestBody HisService service) {
        try {
            serviceService.removeById(service.getId());
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            log.error("remove方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }
}
