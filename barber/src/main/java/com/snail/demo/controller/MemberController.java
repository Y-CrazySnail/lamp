package com.snail.demo.controller;

import com.snail.conreoller.BaseController;
import com.snail.demo.entity.Member;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo-member")
public class MemberController extends BaseController<Member> {

}
