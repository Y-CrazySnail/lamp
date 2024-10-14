package com.yeem.car.controller.manager;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.car.entity.CFProduct;
import com.yeem.car.log.OperateLog;
import com.yeem.car.service.ICFProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cf-product")
public class CFProductController {

    @Autowired
    private ICFProductService productService;

    @OperateLog(operateModule = "产品模块", operateType = "查询", operateDesc = "分页查询产品信息")
    @GetMapping("/page")
    public ResponseEntity<Object> page(@RequestParam("current") int current,
                                       @RequestParam("size") int size) {
        try {
            IPage<CFProduct> page = new Page<>(current, size);
            page = productService.page(page);
            return ResponseEntity.ok(page);
        } catch (Exception e) {
            log.error("产品-查询", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("产品-分页查询失败");
        }
    }

    @OperateLog(operateModule = "产品模块", operateType = "新增", operateDesc = "新增产品信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CFProduct product) {
        try {
            return ResponseEntity.ok(productService.save(product));
        } catch (Exception e) {
            log.error("产品-新增", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("产品-新增失败");
        }
    }

    @OperateLog(operateModule = "产品模块", operateType = "更新", operateDesc = "更新产品信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CFProduct product) {
        try {
            return ResponseEntity.ok(productService.updateById(product));
        } catch (Exception e) {
            log.error("产品-更新", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("产品-更新失败");
        }
    }

    @OperateLog(operateModule = "产品模块", operateType = "删除", operateDesc = "删除产品信息")
    @DeleteMapping("/remove")
    public ResponseEntity<Object> remove(@RequestBody CFProduct product) {
        try {
            return ResponseEntity.ok(productService.removeById(product.getId()));
        } catch (Exception e) {
            log.error("产品-删除", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("产品-删除失败");
        }
    }
}
