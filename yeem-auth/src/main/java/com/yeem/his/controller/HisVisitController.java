package com.yeem.his.controller;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.his.entity.HisPatient;
import com.yeem.his.entity.HisVisit;
import com.yeem.his.service.IHisPatientService;
import com.yeem.his.service.IHisVisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/his/visit")
public class HisVisitController {

    @Autowired
    private IHisVisitService visitService;
    @Autowired
    private IHisPatientService patientService;

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(visitService.list());
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Object> page() {
        try {
            IPage<HisVisit> page = new Page<>();
            LambdaQueryWrapper<HisVisit> queryWrapper = new LambdaQueryWrapper<>();
            return ResponseEntity.ok(visitService.page(page, queryWrapper));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(visitService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody HisVisit visit) {
        try {
            if (!Objects.isNull(visit.getPatient())) {
                HisPatient patient = visit.getPatient();
                if (!Objects.isNull(patient.getPatientIdNo())) {
                    LambdaQueryWrapper<HisPatient> patientWrapper = new LambdaQueryWrapper<>();
                    patientWrapper.eq(HisPatient::getPatientIdNo, patient.getPatientIdNo());
                    int count = patientService.count(patientWrapper);
                    if (count == 0) {
                        patientService.save(patient);
                    } else {
                        patient = patientService.getOne(patientWrapper);
                    }
                } else {
                    patientService.save(patient);
                }
                visit.setPatientId(patient.getId());
                visit.setPatientName(patient.getPatientName());
            }
            visitService.save(visit);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody HisVisit visit) {
        try {
            visitService.updateById(visit);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> remove(@RequestBody HisVisit visit) {
        try {
            visitService.removeById(visit.getId());
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            log.error("remove方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }
}
