package com.lamp.controller.web;

import com.lamp.service.web.LampServiceMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web/lamp-service-month")
public class LampServiceMonthController {

    @Autowired
    private LampServiceMonthService lampServiceMonthService;

}
