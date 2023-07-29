package com.yeem.zero.controller;

import cn.hutool.http.HttpStatus;
import com.yeem.common.conreoller.BaseController;
import com.yeem.zero.entity.ZeroProduct;
import com.yeem.zero.service.IZeroProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/zero-product")
public class ZeroProductController extends BaseController<ZeroProduct> {

    @Autowired
    private IZeroProductService zeroProductService;

    /**
     * 实现id查询方法
     *
     * @param id id
     * @return 产品信息
     */
    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam("id") Long id) {
        ZeroProduct zeroProduct;
        try {
            zeroProduct = zeroProductService.getById(id);
            return ResponseEntity.ok(zeroProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询失败");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> add(@RequestBody ZeroProduct zeroProduct) {
        try {
            zeroProductService.addProduct(zeroProduct);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("新增失败");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody ZeroProduct zeroProduct) {
        try {
            if (StringUtils.isEmpty(zeroProduct.getName())) {
                log.error("商品ID:{}, [商品名]为空", zeroProduct.getId());
                throw new RuntimeException();
            }
            if (!StringUtils.isEmpty(zeroProduct.getPrice())) {
                log.error("商品ID:{}, [商品价格]为空", zeroProduct.getId());
                throw new RuntimeException();
            }
            if (!StringUtils.isEmpty(zeroProduct.getStock())) {
                log.error("商品ID:{}, [商品销量]为空", zeroProduct.getId());
                throw new RuntimeException();
            }
            zeroProductService.updateProduct(zeroProduct);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("不能更新");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> remove(@RequestBody ZeroProduct zeroProduct){
        try {
            zeroProductService.removeProduct(zeroProduct);
            return ResponseEntity.ok(" ");
        }catch (Exception e){
         return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }
}
