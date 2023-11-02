package com.yeem.car_film_saas.controller;

import cn.hutool.http.HttpStatus;
import com.yeem.car_film_saas.entity.CarFilmMessage;
import com.yeem.car_film_saas.service.ICarFilmMessageService;
import com.yeem.car_film_saas.log.OperateLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car-film-message")
public class CarFilmMessageController {
    @Autowired
    private ICarFilmMessageService iCarFilmMessageService;

    @OperateLog(operateModule = "message模块", operateType = "list查询", operateDesc = "描述:查询message全部信息")
    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(value = "productNo", required = false) String productNo, @RequestParam(value = "sendStatus", required = false) String sendStatus, @RequestParam(value = "name", required = false) String name) {
        try {
            return ResponseEntity.ok(iCarFilmMessageService.list(productNo, sendStatus, name));
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }

    @OperateLog(operateModule = "message模块", operateType = "pages查询", operateDesc = "描述:分页查询message全部信息")
    @GetMapping("/pages")
    public ResponseEntity<Object> pages(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam(value = "productNo", required = false) String productNo, @RequestParam(value = "sendStatus", required = false) String sendStatus, @RequestParam(value = "name", required = false) String name) {
        try {
            return ResponseEntity.ok(iCarFilmMessageService.pages(current, size, productNo, sendStatus, name));
        } catch (Exception e) {
            log.error("page方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("分页查询所有失败");
        }
    }

    @OperateLog(operateModule = "message模块", operateType = "getById查询", operateDesc = "描述:ID查询message全部信息")
    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(iCarFilmMessageService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    @OperateLog(operateModule = "message模块", operateType = "delete", operateDesc = "描述:软删除message全部信息")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody CarFilmMessage carFilmMessage) {
        try {
            iCarFilmMessageService.remove(carFilmMessage);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("delete方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }

    @OperateLog(operateModule = "message模块", operateType = "save", operateDesc = "描述:保存message全部信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CarFilmMessage carFilmMessage) {
        try {
            iCarFilmMessageService.save(carFilmMessage);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("save方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("增加失败");
        }
    }

    @OperateLog(operateModule = "message模块", operateType = "update", operateDesc = "描述:修改message全部信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CarFilmMessage carFilmMessage) {
        try {
            iCarFilmMessageService.update(carFilmMessage);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更改失败");
        }
    }
}
