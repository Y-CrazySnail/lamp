package com.yeem.tank.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.tank.entity.TankDemo;
import com.yeem.tank.service.ITankDemoService;
import com.yeem.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tank-demo")
public class  TankDemoController extends BaseController<TankDemo> {

    @Autowired
    private ITankDemoService demoService;

    @PostMapping("getLast")
    public ResponseEntity<Object> getLast() {
        QueryWrapper<TankDemo> demoQueryWrapper = new QueryWrapper<>();
        demoQueryWrapper.orderByDesc("create_time");
        List<TankDemo> demoList = demoService.list(demoQueryWrapper);
        return ResponseEntity.ok(demoList.get(0));
    }
}
