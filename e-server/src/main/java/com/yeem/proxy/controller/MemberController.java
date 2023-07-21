package com.yeem.proxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.conreoller.BaseController;
import com.yeem.proxy.entity.Member;
import com.yeem.proxy.service.IMemberService;
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
                                          String dataOver,
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
        if ("1".equals(dataOver)) {
            queryWrapper.lt("traffic_surplus_month", 0);
        }
        if ("0".equals(dataOver)) {
            queryWrapper.gt("traffic_surplus_month", 0);
        }
        queryWrapper.orderByAsc("end");
        return super.getPage(current, size, queryWrapper);
    }
}
