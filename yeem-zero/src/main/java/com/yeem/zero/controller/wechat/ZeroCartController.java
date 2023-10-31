package com.yeem.zero.controller.wechat;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yeem.zero.log.OperateLog;
import com.yeem.zero.entity.ZeroCart;
import com.yeem.zero.security.WechatAuthInterceptor;
import com.yeem.zero.service.IZeroCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信小程序-购物车信息
 */
@Slf4j
@RestController
@RequestMapping("/wechat/zero-cart")
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
            Long userId = WechatAuthInterceptor.getUserId();
            List<ZeroCart> zeroCartList = zeroCartService.listByUserId(userId);
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
    @OperateLog(operateModule = "购物车模块", operateType = "保存", operateDesc = "保存购物车")
    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody ZeroCart zeroCart) {
        try {
            Long userId = WechatAuthInterceptor.getUserId();
            zeroCart.setUserId(userId);
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
    @OperateLog(operateModule = "购物车模块", operateType = "更新", operateDesc = "更新购物车")
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
    @OperateLog(operateModule = "购物车模块", operateType = "删除", operateDesc = "删除购物车")
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

    /**
     * 批量删除购物车信息
     *
     * @return 批量删除状态
     * @apiNote 批量删除购物车信息
     */
    @OperateLog(operateModule = "购物车模块", operateType = "批量删除", operateDesc = "批量删除购物车")
    @DeleteMapping("batchRemove")
    public ResponseEntity<Object> batchRemove(@RequestBody ZeroCart zeroCart) {
        try {
            zeroCartService.removeByIds(zeroCart.getIdList());
            return ResponseEntity.ok("batch remove cart success");
        } catch (Exception e) {
            log.error("batch remove cart error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("batch remove cart error");
        }
    }

    /**
     * 购物车全选
     *
     * @return 购物车全选状态
     * @apiNote 购物车全选
     */
    @PutMapping("check-all")
    public ResponseEntity<Object> checkAll() {
        try {
            UpdateWrapper<ZeroCart> zeroCartUpdateWrapper = new UpdateWrapper<>();
            zeroCartUpdateWrapper.set("check_flag", "1");
            zeroCartService.update(zeroCartUpdateWrapper);
            return ResponseEntity.ok("check all cart success");
        } catch (Exception e) {
            log.error("check all cart error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("check all cart error");
        }
    }
}
