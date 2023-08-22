package com.yeem.zero.controller.wechat;

import com.yeem.zero.entity.ZeroCart;
import com.yeem.zero.service.IZeroCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车信息
 */
@Slf4j
@RestController
@RequestMapping("/wechat-zero-cart")
public class ZeroCartController {

    @Autowired
    private IZeroCartService zeroCartService;

    /**
     * 根据用户名查询购物车列表
     *
     * @return 购物车列表信息
     * @apiNote 根据用户名查询购物车列表
     */
    @GetMapping("list")
    public ResponseEntity<List<ZeroCart>> list() {
        try {
            List<ZeroCart> zeroCartList = zeroCartService.listByUsername();
            return ResponseEntity.ok(zeroCartList);
        } catch (Exception e) {
            log.error("list cart error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 保存购物车信息
     *
     * @return 保存状态
     * @apiNote 保存购物车信息
     */
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

    /**
     * 更新购物车信息
     *
     * @return 更新状态
     * @apiNote 更新购物车信息
     */
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

    /**
     * 删除购物车信息
     *
     * @return 删除状态
     * @apiNote 删除购物车信息
     */
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
