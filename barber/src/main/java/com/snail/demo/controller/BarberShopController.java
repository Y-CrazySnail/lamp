package com.snail.demo.controller;

import com.snail.conreoller.BaseController;
import com.snail.demo.entity.BarberShop;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo-barber-shop")
public class BarberShopController extends BaseController<BarberShop> {

}
