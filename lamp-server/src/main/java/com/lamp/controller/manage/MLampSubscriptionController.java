package com.lamp.controller.manage;

import com.lamp.entity.LampService;
import com.lamp.service.manage.MLampSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage/lamp-subscription")
public class MLampSubscriptionController {

    @Autowired
    private MLampSubscriptionService lampSubscriptionService;

}
