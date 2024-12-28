package com.lamp.controller.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.service.manage.MLampMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/manage/lamp-member")
public class MLampMemberController {

    @Autowired
    private MLampMemberService memberService;

    @GetMapping("/page")
    public ResponseEntity<Object> page(LampMember member) {
        try {
            IPage<LampMember> page = new Page<>();
            page.setSize(member.getSize());
            page.setCurrent(member.getCurrent());
            LambdaQueryWrapper<LampMember> queryWrapper = new LambdaQueryWrapper<>(LampMember.class);
            BaseEntity.setDeleteFlagCondition(queryWrapper);
            if (Objects.nonNull(member.getKeywords())) {
                queryWrapper.and(LambdaQueryWrapper -> LambdaQueryWrapper
                        .like(LampMember::getWechat, member.getKeywords())
                        .or()
                        .like(LampMember::getEmail, member.getKeywords())
                );
            }
            return ResponseEntity.ok(memberService.page(page, queryWrapper));
        } catch (Exception e) {
            log.error("获取会员列表失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取会员列表失败");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam(value = "id") Long id) {
        try {
            return ResponseEntity.ok(memberService.getById(id));
        } catch (Exception e) {
            log.error("获取会员详情失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取会员详情失败");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> addMember(@RequestBody LampMember member) {
        try {
            memberService.save(member);
            return ResponseEntity.ok("会员添加成功");
        } catch (Exception e) {
            log.error("添加会员失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加会员失败");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateMember(@RequestBody LampMember member) {
        try {
            memberService.updateById(member);
            return ResponseEntity.ok("会员信息更新成功");
        } catch (Exception e) {
            log.error("更新会员信息失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新会员信息失败");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> remove(@RequestBody LampMember member) {
        try {
            memberService.removeById(member.getId());
            return ResponseEntity.ok("会员删除成功");
        } catch (Exception e) {
            log.error("删除会员失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除会员失败");
        }
    }
}
