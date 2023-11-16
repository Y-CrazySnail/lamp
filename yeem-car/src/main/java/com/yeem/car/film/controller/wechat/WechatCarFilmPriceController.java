package com.yeem.car.film.controller.wechat;

import cn.hutool.http.HttpStatus;
import com.yeem.car.film.entity.CarFilmPrice;
import com.yeem.car.film.log.OperateLog;
import com.yeem.car.film.service.ICarFilmPriceService;
import com.yeem.car.film.service.ICarLevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/wechat/car-film-price")
public class WechatCarFilmPriceController {
    @Autowired
    private ICarFilmPriceService carFilmPriceService;
    @Autowired
    private ICarLevelService carLevelService;

    @OperateLog(operateModule = "price模块", operateType = "list查询", operateDesc = "描述:查询price全部信息")
    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(value = "productNo", required = false) String productNo, @RequestParam(value = "productLevelNo", required = false) String productLevelNo, @RequestParam(value = "carLevelNo", required = false) String carLevelNo) {
        try {
            return ResponseEntity.ok(carFilmPriceService.list(productNo, productLevelNo, carLevelNo));
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    @GetMapping("/level-list")
    public ResponseEntity<Object> levelList() {
        try {
            return ResponseEntity.ok(carLevelService.list());
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }


    @OperateLog(operateModule = "price模块", operateType = "pages查询", operateDesc = "描述:分页查询price全部信息")
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current,
                                        @RequestParam("size") int size,
                                        @RequestParam(value = "productNo", required = false) String productNo,
                                        @RequestParam(value = "productLevelNo", required = false) String productLevelNo,
                                        @RequestParam(value = "carLevelNo", required = false) String carLevelNo) {
        try {
            return ResponseEntity.ok(carFilmPriceService.pages(current, size, productNo, productLevelNo, carLevelNo));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    @OperateLog(operateModule = "price模块", operateType = "getById查询", operateDesc = "描述:ID查询price全部信息")
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(carFilmPriceService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    @OperateLog(operateModule = "price模块", operateType = "delete", operateDesc = "描述:软删除price全部信息")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody CarFilmPrice carFilmPrice) {
        try {
            carFilmPriceService.remove(carFilmPrice);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }

    @OperateLog(operateModule = "price模块", operateType = "save", operateDesc = "描述:保存price全部信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CarFilmPrice carFilmPrice) {
        try {
            carFilmPriceService.save(carFilmPrice);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    @OperateLog(operateModule = "price模块", operateType = "update", operateDesc = "描述:修改price全部信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarFilmPrice carFilmPrice) {
        try {
            carFilmPriceService.update(carFilmPrice);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }
}
