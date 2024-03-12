package com.yeem.zero.controller.wechat;

import cn.hutool.http.HttpStatus;
import com.yeem.zero.log.OperateLog;
import com.yeem.zero.entity.ZeroProduct;
import com.yeem.zero.service.IZeroProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信小程序-商品信息
 */
@Slf4j
@RestController
@RequestMapping("/wechat/zero-product")
public class ZeroProductController {

    @Autowired
    private IZeroProductService zeroProductService;

    /**
     * 根据名称模糊查询商品列表
     *
     * @param name 名称
     * @return 商品信息列表
     * @apiNote 根据名称模糊查询商品列表
     */
    @GetMapping("/list-by-name")
    public ResponseEntity<Object> listByName(@RequestParam("name") String name) {
        try {
            List<ZeroProduct> zeroProductList = zeroProductService.listByName(name);
            return ResponseEntity.ok(zeroProductList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("");
        }
    }

    /**
     * 查询推荐商品列表
     *
     * @return 推荐商品列表
     * @apiNote 查询推荐商品列表
     */
    @GetMapping("/recommend")
    public ResponseEntity<Object> recommend() {
        try {
            List<ZeroProduct> zeroProductList = zeroProductService.recommend();
            return ResponseEntity.ok(zeroProductList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("");
        }
    }

    /**
     * 根据商品ID查询
     *
     * @param id 商品ID
     * @return 商品信息
     * @apiNote 根据商品ID查询
     */
    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam("id") Long id) {
        ZeroProduct zeroProduct;
        try {
            zeroProduct = zeroProductService.getById(id);
            return ResponseEntity.ok(zeroProduct);
        } catch (Exception e) {
            log.error("get product error:", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询失败");
        }
    }

    /**
     * 更新商品信息
     *
     * @param zeroProduct 商品信息
     * @return 更新状态
     * @apiNote 更新商品信息
     */
    @OperateLog(operateModule = "商品模块", operateType = "更新商品信息", operateDesc = "更新商品信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody ZeroProduct zeroProduct) {
        try {
            if (StrUtil.isEmpty(zeroProduct.getName())) {
                log.error("商品ID:{}, [商品名]为空", zeroProduct.getId());
                throw new RuntimeException();
            }
            if (null != zeroProduct.getPrice()) {
                log.error("商品ID:{}, [商品价格]为空", zeroProduct.getId());
                throw new RuntimeException();
            }
            if (null != zeroProduct.getStock()) {
                log.error("商品ID:{}, [商品销量]为空", zeroProduct.getId());
                throw new RuntimeException();
            }
            zeroProductService.update(zeroProduct);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("不能更新");
        }
    }

    /**
     * 删除商品信息
     *
     * @param zeroProduct 商品信息
     * @return 删除状态
     * @apiNote 删除商品信息
     */
    @OperateLog(operateModule = "商品模块", operateType = "删除商品信息", operateDesc = "删除商品信息")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> remove(@RequestBody ZeroProduct zeroProduct) {
        try {
            zeroProductService.removeProduct(zeroProduct);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }
}
