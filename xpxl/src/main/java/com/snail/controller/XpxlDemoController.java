package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.conreoller.BaseController;
import com.snail.entity.XpxlDemo;
import com.snail.service.IXpxlDemoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
@Api(value = "案例接口", tags = "案例接口")
public class XpxlDemoController extends BaseController<XpxlDemo> {

    @Autowired
    private IXpxlDemoService demoService;

    @PostMapping("getLast")
    public ResponseEntity<Object> getLast() {
        QueryWrapper<XpxlDemo> demoQueryWrapper = new QueryWrapper<>();
        demoQueryWrapper.orderByDesc("create_time");
        List<XpxlDemo> demoList = demoService.list(demoQueryWrapper);
        return ResponseEntity.ok(demoList.get(0));
    }
}
