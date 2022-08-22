package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.conreoller.BaseController;
import com.snail.entity.Member;
import com.snail.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController extends BaseController<Member> {

    @Autowired
    private IMemberService memberService;

    @GetMapping("memberPage")
    public ResponseEntity<Object> getPage(Integer current,
                                          Integer size,
                                          String wechat,
                                          String qq,
                                          String createUser,
                                          Long id) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(wechat)) {
            queryWrapper.like("wechat", wechat);
        }
        if (!StringUtils.isEmpty(qq)) {
            queryWrapper.like("qq", qq);
        }
        if (!StringUtils.isEmpty(createUser)) {
            queryWrapper.eq("create_user", createUser);
        }
        if (!StringUtils.isEmpty(id)) {
            queryWrapper.eq("id", id);
        }
        queryWrapper.orderByAsc("end");
        return super.getPage(current, size, queryWrapper);
    }
}
