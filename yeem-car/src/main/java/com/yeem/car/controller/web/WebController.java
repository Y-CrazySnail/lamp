package com.yeem.car.controller.web;

import com.yeem.car.service.ICarBrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web")
public class WebController {
    @Autowired
    private ICarBrandService carBrandService;


}
