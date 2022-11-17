package com.snail.aili.controller;

import com.snail.aili.entity.AiliPrice;
import com.snail.conreoller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aili-price")
public class AiliPriceController extends BaseController<AiliPrice> {

}
