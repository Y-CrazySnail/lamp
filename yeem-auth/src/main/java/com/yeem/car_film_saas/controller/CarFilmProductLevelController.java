package com.yeem.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.yeem.car_film_saas.entity.CarFilmProductLevel;
import com.yeem.car_film_saas.service.ICarFilmProductLevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car-file-product-level")
public class CarFilmProductLevelController {
    @Autowired
    private ICarFilmProductLevelService carFilmProductLevelService;

    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(value = "productNo", required = false) String productNo, @RequestParam(value = "productLevelName", required = false) String productLevelName, @RequestParam(value = "status", required = false) String status) {
        try {
            return ResponseEntity.ok(carFilmProductLevelService.list(productNo, productLevelName, status));
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam(value = "productNo", required = false) String productNo, @RequestParam(value = "productLevelName", required = false) String productLevelName, @RequestParam(value = "status", required = false) String status) {
        try {
            return ResponseEntity.ok(carFilmProductLevelService.pages(current, size, productNo, productLevelName, status));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(carFilmProductLevelService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody CarFilmProductLevel carFilmProductLevel) {
        try {
            carFilmProductLevelService.remove(carFilmProductLevel);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CarFilmProductLevel carFilmProductLevel) {
        try {
            carFilmProductLevelService.save(carFilmProductLevel);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarFilmProductLevel carFilmProductLevel) {
        try {
            carFilmProductLevelService.update(carFilmProductLevel);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }
}
