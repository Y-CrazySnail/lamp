package com.yeem.zero.controller.web;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.common.entity.BaseEntity;
import com.yeem.log.OperateLog;
import com.yeem.zero.config.Constant;
import com.yeem.zero.entity.ZeroProduct;
import com.yeem.zero.service.IZeroProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端-商品信息
 */
@Slf4j
@RestController
@RequestMapping("/manager-zero-product")
public class ZeroMProductController {

    @Autowired
    private IZeroProductService zeroProductService;

    /**
     * 分页查询商品
     *
     * @param current    页码
     * @param size       页容量
     * @param name       商品名称
     * @param categoryId 类别ID
     * @return 商品信息列表
     * @apiNote 分页查询商品
     */
    @OperateLog(operateModule = "商品模块", operateType = "模糊查询商品列表", operateDesc = "根据名称模糊查询商品列表")
    @GetMapping("/page")
    public ResponseEntity<IPage<ZeroProduct>> page(@RequestParam(value = "current") Integer current,
                                                   @RequestParam(value = "size") Integer size,
                                                   @RequestParam(value = "name", required = false) String name,
                                                   @RequestParam(value = "categoryId", required = false) Long categoryId) {
        try {
            QueryWrapper<ZeroProduct> zeroProductQueryWrapper = new QueryWrapper<>();
            zeroProductQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
            if (StringUtils.isEmpty(current)) {
                current = 1;
            }
            if (StringUtils.isEmpty(size)) {
                size = 10;
            }
            IPage<ZeroProduct> page = new Page<>(current, size);
            IPage<ZeroProduct> zeroProductIPage = zeroProductService.page(page, zeroProductQueryWrapper);
            return ResponseEntity.ok(zeroProductIPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).build();
        }
    }

    /**
     * 根据商品ID查询
     *
     * @param id 商品ID
     * @return 商品信息
     * @apiNote 根据商品ID查询
     */
    @OperateLog(operateModule = "商品模块", operateType = "查询商品信息", operateDesc = "根据ID查询商品信息")
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
     * 保存商品信息
     *
     * @param zeroProduct 商品信息
     * @return 保存状态
     * @apiNote 保存商品信息
     */
    @OperateLog(operateModule = "商品模块", operateType = "保存商品信息", operateDesc = "保存商品信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody ZeroProduct zeroProduct) {
        try {
            zeroProductService.save(zeroProduct);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("新增失败");
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
            zeroProductService.update(zeroProduct);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更新失败");
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
    @DeleteMapping("/remove")
    public ResponseEntity<Object> delete(@RequestBody ZeroProduct zeroProduct) {
        try {
            zeroProductService.removeById(zeroProduct.getId());
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("删除失败");
        }
    }
}
