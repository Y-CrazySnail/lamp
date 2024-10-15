package com.yeem.car.controller.manager;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.car.entity.CFTenant;
import com.yeem.car.log.OperateLog;
import com.yeem.car.service.ICFTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cf-tenant")
public class CFTenantController {

    @Autowired
    private ICFTenantService tenantService;

    @OperateLog(operateModule = "租户模块", operateType = "查询", operateDesc = "分页查询租户信息")
    @GetMapping("/page")
    public ResponseEntity<Object> page(@RequestParam("current") int current,
                                       @RequestParam("size") int size) {
        try {
            IPage<CFTenant> page = new Page<>(current, size);
            page = tenantService.page(page);
            return ResponseEntity.ok(page);
        } catch (Exception e) {
            log.error("租户-查询", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("租户-分页查询失败");
        }
    }

    @OperateLog(operateModule = "租户模块", operateType = "查询", operateDesc = "查询租户信息列表")
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            List<CFTenant> tenantList = tenantService.list();
            return ResponseEntity.ok(tenantList);
        } catch (Exception e) {
            log.error("租户-查询列表", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("租户-查询列表失败");
        }
    }

    @OperateLog(operateModule = "租户模块", operateType = "新增", operateDesc = "新增租户信息")
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody CFTenant tenant) {
        try {
            return ResponseEntity.ok(tenantService.save(tenant));
        } catch (Exception e) {
            log.error("租户-新增", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("租户-新增失败");
        }
    }

    @OperateLog(operateModule = "租户模块", operateType = "更新", operateDesc = "更新租户信息")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody CFTenant tenant) {
        try {
            return ResponseEntity.ok(tenantService.updateById(tenant));
        } catch (Exception e) {
            log.error("租户-更新", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("租户-更新失败");
        }
    }

    @OperateLog(operateModule = "租户模块", operateType = "删除", operateDesc = "删除租户信息")
    @DeleteMapping("/remove")
    public ResponseEntity<Object> remove(@RequestBody CFTenant tenant) {
        try {
            return ResponseEntity.ok(tenantService.removeById(tenant.getId()));
        } catch (Exception e) {
            log.error("租户-删除", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("租户-删除失败");
        }
    }
}
