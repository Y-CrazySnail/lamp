package com.yeem.xpxl.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.xpxl.entity.XpxlDemo;
import com.yeem.xpxl.service.IXpxlDemoService;
import com.yeem.common.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/xpxl-demo")
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
