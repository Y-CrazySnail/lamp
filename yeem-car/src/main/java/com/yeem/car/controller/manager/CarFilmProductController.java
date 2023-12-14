package com.yeem.car.controller.manager;

import cn.hutool.http.HttpStatus;
import com.yeem.car.entity.CarFilmProduct;
import com.yeem.car.service.ICarFilmProductService;
import com.yeem.car.log.OperateLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car-film-product")
public class CarFilmProductController {
    @Autowired
    private ICarFilmProductService carFilmProductLevelService;

    @OperateLog(operateModule = "product-level模块", operateType = "list查询", operateDesc = "描述:查询product-level全部信息")
    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(value = "productNo", required = false) String productNo,
                                       @RequestParam(value = "productLevelName", required = false) String productLevelName,
                                       @RequestParam(value = "status", required = false) String status) {
        try {
            return ResponseEntity.ok(carFilmProductLevelService.list(productNo, productLevelName, status));
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    @OperateLog(operateModule = "product-level模块", operateType = "pages查询", operateDesc = "描述:分页查询product-level全部信息")
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(
            @RequestParam("current") int current,
            @RequestParam("size") int size,
            @RequestParam(value = "productNo", required = false) String productNo,
            @RequestParam(value = "productLevelName", required = false) String productLevelName,
            @RequestParam(value = "status", required = false) String status) {
        try {
            return ResponseEntity.ok(carFilmProductLevelService.pages(current, size, productNo, productLevelName, status));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    @OperateLog(operateModule = "product-level模块", operateType = "getById查询", operateDesc = "描述:ID查询product-level全部信息")
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(carFilmProductLevelService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    @OperateLog(operateModule = "product-level模块", operateType = "delete", operateDesc = "描述:软删除product-level全部信息")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody CarFilmProduct carFilmProductLevel) {
        try {
            carFilmProductLevelService.remove(carFilmProductLevel);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }

    @OperateLog(operateModule = "product-level模块", operateType = "save", operateDesc = "描述:保存product-level全部信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CarFilmProduct carFilmProductLevel) {
        try {
            carFilmProductLevelService.save(carFilmProductLevel);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    @OperateLog(operateModule = "product-level模块", operateType = "update", operateDesc = "描述:修改product-level全部信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarFilmProduct carFilmProduct) {
        try {
            carFilmProductLevelService.update(carFilmProduct);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }
}
