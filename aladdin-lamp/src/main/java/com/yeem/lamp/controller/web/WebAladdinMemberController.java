package com.yeem.lamp.controller.web;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.common.utils.WebJWTUtils;
import com.yeem.im.dto.SysTelegramSendDTO;
import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinMemberEntity;
import com.yeem.lamp.security.LocalAuthInterceptor;
import com.yeem.lamp.service.IAladdinMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/web/member")
public class WebAladdinMemberController {

    @Autowired
    private IAladdinMemberService aladdinMemberService;
    @Autowired
    private ISysTelegramService sysTelegramService;

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
    public ResponseEntity<Object> update(@RequestBody AladdinMemberEntity aladdinMember) {
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
     * @param aladdinMember aladdinMember
     * @return 登录token
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AladdinMemberEntity aladdinMember) {
        try {
            QueryWrapper<AladdinMemberEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email", aladdinMember.getEmail());
            queryWrapper.eq("password", aladdinMember.getPassword());
            int count = aladdinMemberService.count(queryWrapper);
            if (count > 0) {
                aladdinMember = aladdinMemberService.getOne(queryWrapper);
                String token = WebJWTUtils.generateJWT(aladdinMember.getId());
                try {
                    SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
                    sysTelegramSendDTO.setTemplateName("login");
                    sysTelegramSendDTO.setTemplateType("telegram");
                    Map<String, Object> replaceMap = new HashMap<>();
                    replaceMap.put("email", aladdinMember.getEmail());
                    sysTelegramSendDTO.setReplaceMap(replaceMap);
                    sysTelegramService.send(sysTelegramSendDTO);
                } catch (Exception e) {
                    log.error("send telegram message error:", e);
                }
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            log.error("login error", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("登录失败");
        }
    }
}
