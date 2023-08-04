package com.yeem.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.yeem.car_film_saas.entity.CarFilmQuality;
import com.yeem.car_film_saas.service.ICarFilmQualityService;
import com.yeem.car_film_saas.utils.OperLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car-file-quality")
public class CarFilmQualityController {

    @Autowired
    private ICarFilmQualityService carFilmQualityService;

    @OperLog(operModul = "quality模块", operType = "list查询", operDesc = "描述:查询quality全部信息")
    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "productNo", required = false) String productNo, @RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "qualityCardNo", required = false) String qualityCardNo, @RequestParam(value = "plateNo", required = false) String plateNo, @RequestParam(value = "vin", required = false) String vin, @RequestParam(value = "likeName", required = false) String likeName, @RequestParam(value = "likePhone", required = false) String likePhone, @RequestParam(value = "likeQualityCardNo", required = false) String likeQualityCardNo, @RequestParam(value = "likePlateNo", required = false) String likePlateNo, @RequestParam(value = "likeVin", required = false) String likeVin) {
        try {
            return ResponseEntity.ok(carFilmQualityService.list(name, productNo, phone, qualityCardNo, plateNo, vin, likeName, likePhone, likeQualityCardNo, likePlateNo, likeVin));
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    @OperLog(operModul = "quality模块", operType = "pages查询", operDesc = "描述:分页查询quality全部信息")
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "productNo", required = false) String productNo, @RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "qualityCardNo", required = false) String qualityCardNo, @RequestParam(value = "plateNo", required = false) String plateNo, @RequestParam(value = "vin", required = false) String vin, @RequestParam(value = "likeName", required = false) String likeName, @RequestParam(value = "likePhone", required = false) String likePhone, @RequestParam(value = "likeQualityCardNo", required = false) String likeQualityCardNo, @RequestParam(value = "likePlateNo", required = false) String likePlateNo, @RequestParam(value = "likeVin", required = false) String likeVin) {
        try {
            return ResponseEntity.ok(carFilmQualityService.pages(current, size, name, productNo, phone, qualityCardNo, plateNo, vin, likeName, likePhone, likeQualityCardNo, likePlateNo, likeVin));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    @OperLog(operModul = "quality模块", operType = "getById查询", operDesc = "描述:ID查询quality全部信息")
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(carFilmQualityService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    @OperLog(operModul = "quality模块", operType = "delete", operDesc = "描述:软删除quality全部信息")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody CarFilmQuality carFilmQuality) {
        try {
            carFilmQualityService.remove(carFilmQuality);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }

    @OperLog(operModul = "quality模块", operType = "save", operDesc = "描述:保存quality全部信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CarFilmQuality carFilmQuality) {
        try {
            carFilmQualityService.save(carFilmQuality);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    @OperLog(operModul = "quality模块", operType = "update", operDesc = "描述:修改quality模块全部信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarFilmQuality carFilmQuality) {
        try {
            carFilmQualityService.update(carFilmQuality);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }
}
