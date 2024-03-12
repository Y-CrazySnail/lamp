package com.yeem.zero.controller.wechat;

import com.yeem.zero.entity.SysDictionary;
import com.yeem.zero.entity.ZeroAddress;
import com.yeem.zero.log.OperateLog;
import com.yeem.zero.security.WechatAuthInterceptor;
import com.yeem.zero.service.ISysDictionaryService;
import com.yeem.zero.service.IZeroAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信小程序-字典信息
 */
@Slf4j
@RestController
@RequestMapping("/wechat/sys-dictionary")
public class SysDictionaryController {

    @Autowired
    private ISysDictionaryService sysDictionaryService;

    /**
     * 查询字典列表
     *
     * @return 字典列表
     * @apiNote 查询字典列表
     */
    @GetMapping("list")
    public ResponseEntity<List<SysDictionary>> list() {
        try {
            return ResponseEntity.ok(sysDictionaryService.list());
        } catch (Exception e) {
            log.error("list dictionary error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
