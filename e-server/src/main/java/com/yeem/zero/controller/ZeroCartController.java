package com.yeem.zero.controller;

import com.yeem.conreoller.BaseController;
import com.yeem.zero.entity.ZeroCart;
import com.yeem.zero.service.IZeroCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/zero-cart")
public class ZeroCartController extends BaseController<ZeroCart> {

    @Autowired
    private IZeroCartService zeroCartService;

    @GetMapping("list")
    public ResponseEntity<Object> list() {
        try {
            List<ZeroCart> zeroCartList = zeroCartService.listByUsername();
            return ResponseEntity.ok(zeroCartList);
        } catch (Exception e) {
            log.error("list cart error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("list cart error");
        }
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody ZeroCart zeroCart) {
        try {
            zeroCartService.save(zeroCart);
            return ResponseEntity.ok("save product to cart success");
        } catch (Exception e) {
            log.error("save product to cart error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("save product to cart error");
        }
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody ZeroCart zeroCart) {
        try {
            zeroCartService.updateById(zeroCart);
            return ResponseEntity.ok("update cart success");
        } catch (Exception e) {
            log.error("update cart error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("update cart error");
        }
    }

    @DeleteMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody ZeroCart zeroCart) {
        try {
            zeroCartService.remove(zeroCart);
            return ResponseEntity.ok("remove cart success");
        } catch (Exception e) {
            log.error("remove cart error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("remove cart error");
        }
    }
}
