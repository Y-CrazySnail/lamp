package com.lamp.controller.manage;

import com.lamp.service.manage.MLampServiceMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/lamp-service-month")
public class MLampServiceMonthController {

    @Autowired
    private MLampServiceMonthService lampServiceMonthService;
}
