package com.yeem.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.yeem.car_film_saas.entity.CarFilmPrice;
import com.yeem.car_film_saas.service.ICarFilmPriceService;
import com.yeem.car_film_saas.utils.OperLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car-file-price")
public class CarFilmPriceController {
    @Autowired
    private ICarFilmPriceService iCarFilmPriceService;

    @OperLog(operModul = "price模块", operType = "list查询", operDesc = "描述:查询price全部信息")
    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(value = "productNo", required = false) String productNo, @RequestParam(value = "productLevelNo", required = false) String productLevelNo, @RequestParam(value = "carLevelNo", required = false) String carLevelNo) {
        try {
            return ResponseEntity.ok(iCarFilmPriceService.list(productNo, productLevelNo, carLevelNo));
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    @OperLog(operModul = "price模块", operType = "pages查询", operDesc = "描述:分页查询price全部信息")
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam("productNo") String productNo, @RequestParam("productLevelNo") String productLevelNo, @RequestParam("carLevelNo") String carLevelNo) {
        try {
            return ResponseEntity.ok(iCarFilmPriceService.pages(current, size, productNo, productLevelNo, carLevelNo));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    @OperLog(operModul = "price模块", operType = "getById查询", operDesc = "描述:ID查询price全部信息")
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(iCarFilmPriceService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    @OperLog(operModul = "price模块", operType = "delete", operDesc = "描述:软删除price全部信息")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody CarFilmPrice carFilmPrice) {
        try {
            iCarFilmPriceService.remove(carFilmPrice);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }

    @OperLog(operModul = "price模块", operType = "save", operDesc = "描述:保存price全部信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CarFilmPrice carFilmPrice) {
        try {
            iCarFilmPriceService.save(carFilmPrice);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    @OperLog(operModul = "price模块", operType = "update", operDesc = "描述:修改price全部信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarFilmPrice carFilmPrice) {
        try {
            iCarFilmPriceService.update(carFilmPrice);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }
}
