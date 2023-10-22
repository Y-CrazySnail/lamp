package com.yeem.zero.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.log.OperateLog;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端-用户信息
 */
@Slf4j
@RestController
@RequestMapping("/manager-zero-user")
public class ZeroMUserController {

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    /**
     * 订单分页查询
     *
     * @param current 当前页
     * @param size    页码
     * @return 订单分页
     */
    @GetMapping("page")
    public ResponseEntity<IPage<ZeroUserExtra>> getPage(@RequestParam("current") Integer current,
                                                    @RequestParam("size") Integer size,
                                                    @RequestParam(value = "userId", required = false) Long userId,
                                                    @RequestParam(value = "nickName", required = false) String nickName) {
        if (StringUtils.isEmpty(current)) {
            current = 1;
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        IPage<ZeroUserExtra> page = new Page<>(current, size);
        QueryWrapper<ZeroUserExtra> zeroUserExtraQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userId)) {
            zeroUserExtraQueryWrapper.eq("id", userId);
        }
        if (!StringUtils.isEmpty(nickName)) {
            zeroUserExtraQueryWrapper.eq("nick_name", nickName);
        }
        try {
            return ResponseEntity.ok(zeroUserExtraService.page(page, zeroUserExtraQueryWrapper));
        } catch (Exception e) {
            log.error("get user page error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
