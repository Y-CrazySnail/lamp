package com.yeem.zero.controller.web;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.common.aspect.log.OperateLog;
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
     * @return 商品信息列表
     * @apiNote 分页查询商品
     */
    @OperateLog(operateModule = "商品模块", operateType = "模糊查询商品列表", operateDesc = "根据名称模糊查询商品列表")
    @GetMapping("/page")
    public ResponseEntity<IPage<ZeroProduct>> page(Integer current, Integer size) {
        try {
            QueryWrapper<ZeroProduct> zeroProductQueryWrapper = new QueryWrapper<>();
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
}
