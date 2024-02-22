package com.yeem.one.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.one.entity.OneTenant;
import com.yeem.one.service.IOneTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端-租户
 */
@Slf4j
@RestController
@RequestMapping("/manager/one/tenant")
public class OneTenantController {

    @Autowired
    private IOneTenantService oneTenantService;

    /**
     * 订单分页查询
     *
     * @param current 当前页
     * @param size    页码
     * @return 租户分页
     */
    @GetMapping("page")
    public ResponseEntity<IPage<OneTenant>> getPage(@RequestParam("current") Integer current,
                                                    @RequestParam("size") Integer size) {
        if (StringUtils.isEmpty(current)) {
            current = 1;
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        IPage<OneTenant> page = new Page<>(current, size);
        QueryWrapper<OneTenant> queryWrapper = new QueryWrapper<>();
        try {
            return ResponseEntity.ok(oneTenantService.page(page, queryWrapper));
        } catch (Exception e) {
            log.error("get user page error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
