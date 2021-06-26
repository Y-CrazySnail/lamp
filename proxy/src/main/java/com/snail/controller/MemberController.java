package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Member;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController extends BaseController<Member> {

}
