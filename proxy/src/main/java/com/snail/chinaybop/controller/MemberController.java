package com.snail.chinaybop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.conreoller.BaseController;
import com.snail.chinaybop.entity.Member;
import com.snail.chinaybop.service.IMemberService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "分页查询接口")
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
