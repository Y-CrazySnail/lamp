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
                                                    @RequestParam(value = "username", required = false) String username,
                                                    @RequestParam(value = "nickName", required = false) String nickName) {
        if (StringUtils.isEmpty(current)) {
            current = 1;
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        IPage<ZeroUserExtra> page = new Page<>(current, size);
        QueryWrapper<ZeroUserExtra> zeroUserExtraQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(username)) {
            zeroUserExtraQueryWrapper.eq("username", username);
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

    /**
     * 查询用户信息
     *
     * @return 用户信息
     * @apiNote 查询用户信息
     */
    @OperateLog(operateModule = "用户模块", operateType = "查询用户信息", operateDesc = "查询用户信息")
    @GetMapping("get")
    public ResponseEntity<ZeroUserExtra> get() {
        String username = OauthUtils.getUsername();
        if (StringUtils.isEmpty(username)) {
            log.error("get username error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ZeroUserExtra userExtra;
        try {
            userExtra = zeroUserExtraService.get(username);
        } catch (Exception e) {
            log.error("get user extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(userExtra);
    }

    /**
     * 更新用户信息
     *
     * @return 用户信息
     * @apiNote 更新用户信息
     */
    @OperateLog(operateModule = "用户模块", operateType = "更新用户信息", operateDesc = "更新用户信息")
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody ZeroUserExtra entity) {
        ZeroUserExtra zeroUserExtra;
        try {
            zeroUserExtra = zeroUserExtraService.update(entity);
        } catch (Exception e) {
            log.error("update user extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(zeroUserExtra);
    }

    /**
     * 分销团队查询
     *
     * @param nickName 昵称（模糊查询）
     * @return 分销团队用户信息
     * @apiNote 分销团队查询
     */
    @OperateLog(operateModule = "用户模块", operateType = "分销团队查询", operateDesc = "分销团队查询")
    @GetMapping("distribution")
    public ResponseEntity<List<ZeroUserExtra>> distribution(@RequestParam("nickName") String nickName) {
        String username = OauthUtils.getUsername();
        if (StringUtils.isEmpty(username)) {
            log.error("get username error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        List<ZeroUserExtra> zeroUserExtraList;
        try {
            zeroUserExtraList = zeroUserExtraService.distribution(username, nickName);
        } catch (Exception e) {
            log.error("update user extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(zeroUserExtraList);
    }
}
