package com.yeem.zero.controller.wechat;

import com.yeem.common.aspect.log.OperateLog;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.entity.ZeroFavorite;
import com.yeem.zero.service.IZeroFavoriteService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信小程序-收藏信息
 */
@Slf4j
@RestController
@RequestMapping("/wechat-zero-favorite")
public class ZeroFavoriteController {

    @Autowired
    private IZeroFavoriteService zeroFavoriteService;

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    /**
     * 根据用户名查询收藏列表
     *
     * @return 收藏列表信息
     * @apiNote 根据用户名查询收藏列表
     */
    @OperateLog(operateModule = "收藏模块", operateType = "查询列表", operateDesc = "查询收藏列表")
    @GetMapping("list")
    public ResponseEntity<List<ZeroFavorite>> list() {
        try {
            List<ZeroFavorite> zeroFavoriteList = zeroFavoriteService.listByUsername(OauthUtils.getUsername());
            return ResponseEntity.ok(zeroFavoriteList);
        } catch (Exception e) {
            log.error("list favorite error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 保存收藏信息
     *
     * @return 保存状态
     * @apiNote 保存收藏信息
     */
    @OperateLog(operateModule = "收藏模块", operateType = "保存", operateDesc = "保存收藏信息")
    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody ZeroFavorite zeroFavorite) {
        try {
            zeroFavoriteService.save(zeroFavorite);
            return ResponseEntity.ok("save product to favorite success");
        } catch (Exception e) {
            log.error("save product to favorite error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("save product to favorite error");
        }
    }

    /**
     * 删除收藏信息
     *
     * @return 删除状态
     * @apiNote 删除收藏信息
     */
    @OperateLog(operateModule = "收藏模块", operateType = "删除", operateDesc = "删除收藏")
    @DeleteMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody ZeroFavorite zeroFavorite) {
        try {
            zeroFavoriteService.removeById(zeroFavorite.getId());
            return ResponseEntity.ok("remove favorite success");
        } catch (Exception e) {
            log.error("remove favorite error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("remove favorite error");
        }
    }
}
