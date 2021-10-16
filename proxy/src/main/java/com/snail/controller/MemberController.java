package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.snail.conreoller.BaseController;
import com.snail.entity.Member;
import com.snail.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController extends BaseController<Member> {

    @Autowired
    private IMemberService memberService;

    @PostMapping("resetIos")
    public ResponseEntity<Object> resetIos(@RequestBody Member entity) {
        UpdateWrapper<Member> memberUpdateWrapper = new UpdateWrapper<>();
        memberUpdateWrapper.set("iphone_num", 0);
        memberUpdateWrapper.eq("id", entity.getId());
        memberService.update(memberUpdateWrapper);
        return ResponseEntity.ok("重置成功");
    }

    @PostMapping("resetWin")
    public ResponseEntity<Object> resetWin(@RequestBody Member entity) {
        UpdateWrapper<Member> memberUpdateWrapper = new UpdateWrapper<>();
        memberUpdateWrapper.set("windows_num", 0);
        memberUpdateWrapper.eq("id", entity.getId());
        memberService.update(memberUpdateWrapper);
        return ResponseEntity.ok("重置成功");
    }

    @PostMapping("resetMac")
    public ResponseEntity<Object> resetMac(@RequestBody Member entity) {
        UpdateWrapper<Member> memberUpdateWrapper = new UpdateWrapper<>();
        memberUpdateWrapper.set("mac_num", 0);
        memberUpdateWrapper.eq("id", entity.getId());
        memberService.update(memberUpdateWrapper);
        return ResponseEntity.ok("重置成功");
    }

    @PostMapping("resetAndroid")
    public ResponseEntity<Object> resetAndroid(@RequestBody Member entity) {
        UpdateWrapper<Member> memberUpdateWrapper = new UpdateWrapper<>();
        memberUpdateWrapper.set("android_num", 0);
        memberUpdateWrapper.eq("id", entity.getId());
        memberService.update(memberUpdateWrapper);
        return ResponseEntity.ok("重置成功");
    }
}
