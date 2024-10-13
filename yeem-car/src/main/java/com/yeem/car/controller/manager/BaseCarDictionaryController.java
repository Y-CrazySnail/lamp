package com.yeem.car.controller.manager;

import cn.hutool.http.HttpStatus;
import com.yeem.car.entity.BaseCarDictionary;
import com.yeem.car.service.ICarDictionaryService;
import com.yeem.common.conreoller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/base-car-dictionary")
public class BaseCarDictionaryController extends BaseController<BaseCarDictionary> {
    @Autowired
    private ICarDictionaryService carDictionaryService;

    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(required = false, value = "dictType") String dictType,
                                       @RequestParam(required = false, value = "productNo") String productNo) {
        try {
            return ResponseEntity.ok(carDictionaryService.list(dictType, productNo));
        } catch (Exception e) {
            log.error("list方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询所有失败");
        }
    }
}
