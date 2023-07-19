package com.snail.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.snail.car_film_saas.entity.CarFilmMessage;
import com.snail.car_film_saas.entity.CarFilmQuality;
import com.snail.car_film_saas.service.ICarFilmQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car-file-quality")
public class CarFilmQualityController {

    @Autowired
    private ICarFilmQualityService carFilmQualityService;
    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(value = "name",required = false) String name, @RequestParam(value = "productNo",required = false) String productNo, @RequestParam(value = "phone",required = false) String phone, @RequestParam(value = "qualityCardNo",required = false) String qualityCardNo, @RequestParam(value = "plateNo",required = false) String plateNo, @RequestParam(value = "vin",required = false) String vin, @RequestParam(value = "likePhone",required = false) String likePhone, @RequestParam(value = "likeQualityCardNo",required = false) String likeQualityCardNo, @RequestParam(value = "likePlateNo",required = false) String likePlateNo, @RequestParam(value = "likeVin",required = false) String likeVin) {
        try {
            return ResponseEntity.ok(carFilmQualityService.list(name, productNo, phone, qualityCardNo, plateNo, vin, likePhone, likeQualityCardNo, likePlateNo, likeVin));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam(value = "name",required = false) String name, @RequestParam(value = "productNo",required = false) String productNo, @RequestParam(value = "phone",required = false) String phone, @RequestParam(value = "qualityCardNo",required = false) String qualityCardNo, @RequestParam(value = "plateNo",required = false) String plateNo, @RequestParam(value = "vin",required = false) String vin, @RequestParam(value = "likePhone",required = false) String likePhone, @RequestParam(value = "likeQualityCardNo",required = false) String likeQualityCardNo, @RequestParam(value = "likePlateNo",required = false) String likePlateNo, @RequestParam(value = "likeVin",required = false) String likeVin) {
        try {
            return ResponseEntity.ok(carFilmQualityService.pages(current,size,name, productNo, phone, qualityCardNo, plateNo, vin, likePhone, likeQualityCardNo, likePlateNo, likeVin));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(carFilmQualityService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody CarFilmQuality carFilmQuality) {
        try {
            carFilmQualityService.remove(carFilmQuality);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CarFilmQuality carFilmQuality) {
        try {
            carFilmQualityService.save(carFilmQuality);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarFilmQuality carFilmQuality) {
        try {
            carFilmQualityService.update(carFilmQuality);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }
}
