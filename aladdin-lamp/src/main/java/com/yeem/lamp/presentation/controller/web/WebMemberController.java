package com.yeem.lamp.presentation.controller.web;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.common.utils.WebJWTUtils;
import com.yeem.im.dto.SysTelegramSendDTO;
import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.application.dto.TokenDTO;
import com.yeem.lamp.application.service.MemberAppService;
import com.yeem.lamp.infrastructure.persistence.entity.MemberEntity;
import com.yeem.lamp.presentation.request.MemberVO;
import com.yeem.lamp.security.LocalAuthInterceptor;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/web/member")
public class WebMemberController {

    @Autowired
    private IAladdinMemberService aladdinMemberService;
    @Autowired
    private ISysTelegramService sysTelegramService;

    @Autowired
    private MemberAppService memberAppService;

    /**
     * 根据ID查询
     *
     * @return 会员信息
     */
    @GetMapping("/getById")
    public ResponseEntity<Object> getById() {
        try {
            Long id = LocalAuthInterceptor.getMemberId();
            if (null == id) {
                return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询 id为空");
            }
            return ResponseEntity.ok(aladdinMemberService.getById(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("按id查询失败");
        }
    }

    /**
     * 更改
     *
     * @param aladdinMember aladdinMember
     * @return 更新结果
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody MemberEntity aladdinMember) {
        try {
            Long id = LocalAuthInterceptor.getMemberId();
            aladdinMember.setId(id);
            aladdinMemberService.updateById(aladdinMember);
            return ResponseEntity.ok(" ");
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更新失败");
        }
    }

    /**
     * 登录
     *
     * @param memberVO memberVO
     * @return 登录token
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody MemberVO memberVO) {
        try {
            TokenDTO tokenDTO = memberAppService.login(memberVO);
            return ResponseEntity.ok(tokenDTO);
        } catch (Exception e) {
            log.error("login error", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("登录失败");
        }
    }
}
