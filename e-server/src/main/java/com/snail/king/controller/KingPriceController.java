package com.snail.king.controller;

import com.snail.conreoller.BaseController;
import com.snail.king.entity.KingPrice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/king-price")
public class KingPriceController extends BaseController<KingPrice> {

}
