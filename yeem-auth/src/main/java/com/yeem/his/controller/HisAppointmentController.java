package com.yeem.his.controller;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.his.entity.HisAppointment;
import com.yeem.his.service.IHisAppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/his/appointment")
public class HisAppointmentController {

    @Autowired
    private IHisAppointmentService appointmentService;

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(appointmentService.list());
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Object> page() {
        try {
            IPage<HisAppointment> page = new Page<>();
            LambdaQueryWrapper<HisAppointment> queryWrapper = new LambdaQueryWrapper<>();
            return ResponseEntity.ok(appointmentService.page(page, queryWrapper));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(appointmentService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody HisAppointment appointment) {
        try {
            appointmentService.save(appointment);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody HisAppointment appointment) {
        try {
            appointmentService.updateById(appointment);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> remove(@RequestBody HisAppointment appointment) {
        try {
            appointmentService.removeById(appointment.getId());
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            log.error("remove方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }
}
