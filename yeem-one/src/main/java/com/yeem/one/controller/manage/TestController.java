package com.yeem.one.controller.manage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @RequestMapping("test")
    public String ok(@RequestParam(value = "test", defaultValue = "1") Integer test) {
        return String.valueOf(test);
    }
}
