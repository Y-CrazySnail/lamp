package com.yeem.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.yeem.car_film_saas.entity.CarFilmTechnician;
import com.yeem.car_film_saas.service.ICarFilmTechnicianService;
import com.yeem.car_film_saas.utils.OperLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car-file-technician")
public class CarFilmTechnicianController {

    @Autowired
    private ICarFilmTechnicianService carFilmTechnicianService;

    @OperLog(operModul = "technician模块", operType = "list查询", operDesc = "描述:查询technician全部信息")
    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(value = "productNo", required = false) String productNo, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "province", required = false) String province, @RequestParam(value = "city", required = false) String city, @RequestParam(value = "county", required = false) String county, @RequestParam(value = "level", required = false) String level) {
        try {
            return ResponseEntity.ok(carFilmTechnicianService.list(productNo, name, province, city, county, level));
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    @OperLog(operModul = "technician模块", operType = "pages查询", operDesc = "描述:分页查询technician全部信息")
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam(value = "productNo", required = false) String productNo, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "province", required = false) String province, @RequestParam(value = "city", required = false) String city, @RequestParam(value = "county", required = false) String county, @RequestParam(value = "level", required = false) String level) {
        try {
            return ResponseEntity.ok(carFilmTechnicianService.pages(current, size, productNo, name, province, city, county, level));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    @OperLog(operModul = "technician模块", operType = "getById查询", operDesc = "描述:ID查询technician全部信息")
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(carFilmTechnicianService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    @OperLog(operModul = "technician模块", operType = "delete", operDesc = "描述:软删除technician全部信息")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody CarFilmTechnician carFilmTechnician) {
        try {
            carFilmTechnicianService.remove(carFilmTechnician);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }

    @OperLog(operModul = "technician模块", operType = "save", operDesc = "描述:保存technician全部信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CarFilmTechnician carFilmTechnician) {
        try {
            carFilmTechnicianService.save(carFilmTechnician);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    @OperLog(operModul = "technician模块", operType = "update", operDesc = "描述:修改qtechnician全部信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarFilmTechnician carFilmTechnician) {
        try {
            carFilmTechnicianService.update(carFilmTechnician);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }
}
