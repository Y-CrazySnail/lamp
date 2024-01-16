package com.yeem.lamp.controller.web;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.common.utils.WebJWTUtils;
import com.yeem.common.utils.WechatJWTUtils;
import com.yeem.lamp.entity.AladdinMember;
import com.yeem.lamp.security.LocalAuthInterceptor;
import com.yeem.lamp.service.IAladdinMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/member")
public class WebAladdinMemberController {

    @Autowired
    private IAladdinMemberService aladdinMemberService;

    /**
     * 根据ID查询
     *
     * @return 会员信息
     */
    @GetMapping("/getById")
    public ResponseEntity<Object> getById() {
        try {
            Long id = LocalAuthInterceptor.getMemberId();
            if (StringUtils.isEmpty(id)) {
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
    public ResponseEntity<Object> update(@RequestBody AladdinMember aladdinMember) {
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
     * 更改
     *
     * @param aladdinMember aladdinMember
     * @return 更新结果
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AladdinMember aladdinMember) {
        try {
            QueryWrapper<AladdinMember> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email", aladdinMember.getEmail());
            queryWrapper.eq("password", aladdinMember.getPassword());
            int count = aladdinMemberService.count(queryWrapper);
            if (count > 0) {
                String token = WebJWTUtils.generateJWT(aladdinMember.getId());
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            log.error("update方法", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("更新失败");
        }
    }
}
