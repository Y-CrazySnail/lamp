package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.conreoller.BaseController;
import com.snail.entity.Employee;
import com.snail.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController extends BaseController<Employee> {

    @Autowired
    private IEmployeeService memberService;

    @GetMapping("memberPage")
    @ApiOperation(value = "分页查询接口")
    public ResponseEntity<Object> getPage(Integer current,
                                          Integer size,
                                          String wechat,
                                          String qq,
                                          String createUser,
                                          Long id) {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
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
