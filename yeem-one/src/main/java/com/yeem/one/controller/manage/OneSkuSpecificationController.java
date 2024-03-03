package com.yeem.one.controller.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneSkuSpecification;
import com.yeem.one.log.OperateLog;
import com.yeem.one.service.IOneSkuSpecificationService;
import com.yeem.one.service.IOneTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端-sku规格
 */
@Slf4j
@RestController
@RequestMapping("/manage/sku-specification")
public class OneSkuSpecificationController {

    @Autowired
    private IOneSkuSpecificationService service;
    @Autowired
    private IOneTenantService oneTenantService;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size    页码
     * @return sku规格分页
     */
    @GetMapping("page")
    public ResponseEntity<IPage<OneSkuSpecification>> getPage(@RequestParam("current") Integer current,
                                                      @RequestParam("size") Integer size) {
        if (StringUtils.isEmpty(current)) {
            current = 1;
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        IPage<OneSkuSpecification> page = new Page<>(current, size);
        LambdaQueryWrapper<OneSkuSpecification> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OneSkuSpecification::getDeleteFlag, Constant.BOOLEAN_FALSE);
        lambdaQueryWrapper.in(OneSkuSpecification::getTenantId, oneTenantService.authorizedTenantIdSet());
        try {
            return ResponseEntity.ok(service.page(page, lambdaQueryWrapper));
        } catch (Exception e) {
            log.error("get sku specification page error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根据ID获取
     *
     * @param id sku规格ID
     * @return sku规格信息
     */
    @GetMapping(value = "getById")
    public ResponseEntity<OneSkuSpecification> getById(@RequestParam(value = "id") Long id) {
        try {
            OneSkuSpecification skuSpecification = service.getById(id);
            oneTenantService.authenticate(skuSpecification.getTenantId());
            return ResponseEntity.ok(skuSpecification);
        } catch (Exception e) {
            log.error("get sku specification by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 新增sku规格
     *
     * @return sku规格
     * @apiNote 新增sku规格
     */
    @OperateLog(operateModule = "sku规格模块", operateType = "新增sku规格", operateDesc = "新增sku规格")
    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody OneSkuSpecification sku) {
        try {
            oneTenantService.authenticate(sku.getTenantId());
            service.save(sku);
        } catch (Exception e) {
            log.error("save sku specification info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(sku);
    }

    /**
     * 更新sku规格信息
     *
     * @return sku规格信息
     * @apiNote 更新sku规格信息
     */
    @OperateLog(operateModule = "sku规格模块", operateType = "更新sku规格信息", operateDesc = "更新sku规格信息")
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody OneSkuSpecification sku) {
        try {
            oneTenantService.authenticate(sku.getTenantId());
            service.updateById(sku);
        } catch (Exception e) {
            log.error("update sku specification info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(sku);
    }

    /**
     * 删除sku规格信息
     *
     * @return sku规格信息
     * @apiNote 删除sku规格信息
     */
    @OperateLog(operateModule = "sku规格模块", operateType = "删除sku规格信息", operateDesc = "删除sku规格信息")
    @DeleteMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody OneSkuSpecification sku) {
        try {
            sku = service.getById(sku.getId());
            oneTenantService.authenticate(sku.getTenantId());
            sku.setDeleteFlag(true);
            service.updateById(sku);
        } catch (Exception e) {
            log.error("remove sku specification info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(sku);
    }
}
