package com.lamp.controller.web;

import com.lamp.entity.LampService;
import com.lamp.service.web.LampSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/lamp-subscription")
public class LampSubscriptionController {

    @Autowired
    private LampSubscriptionService lampSubscriptionService;

}
