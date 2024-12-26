package com.lamp.controller.web;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamp.common.service.DictService;
import com.lamp.entity.LampMember;
import com.lamp.security.LocalAuthInterceptor;
import com.lamp.service.web.LampMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/lamp-member")
public class LampMemberController {

    @Value("${apple.control}")
    private String APPLE_CONTROL;

    @Autowired
    private LampMemberService memberService;

    @Autowired
    private DictService dictService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LampMember member) {
        try {
            return ResponseEntity.ok(memberService.login(member));
        } catch (Exception e) {
            log.error("登录失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("登录失败");
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<Object> signUp(@RequestBody LampMember member) {
        try {
            memberService.signUp(member);
            return ResponseEntity.ok("注册成功");
        } catch (Exception e) {
            log.error("注册失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册失败");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get() {
        try {
            Long id = LocalAuthInterceptor.getMemberId();
            return ResponseEntity.ok(memberService.getById(id));
        } catch (Exception e) {
            log.error("按id查询失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("按id查询失败");
        }
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<Object> updatePassword(@RequestBody LampMember member) {
        try {
            Long id = LocalAuthInterceptor.getMemberId();
            memberService.updatePassword(id, member.getPassword());
            return ResponseEntity.ok("修改密码成功");
        } catch (Exception e) {
            log.error("修改密码失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("修改密码失败");
        }
    }

    /**
     * 列表查询
     *
     * @return apple信息
     */
    @GetMapping("/apple")
    public ResponseEntity<Object> apple() {
        try {
            String appleControl = dictService.getValueByTypeAndKey("apple", "share_url", "");
            HttpResponse response = HttpUtil.createRequest(Method.GET, appleControl).execute();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            return ResponseEntity.ok(jsonNode.get("accounts").get(0));
        } catch (Exception e) {
            log.error("list查询失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("苹果账号信息查询失败");
        }
    }
}
