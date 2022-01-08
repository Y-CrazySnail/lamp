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
}
