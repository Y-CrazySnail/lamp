package com.yeem.king.controller;

import com.yeem.common.conreoller.BaseController;
import com.yeem.king.entity.KingPrice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/king-price")
public class KingPriceController extends BaseController<KingPrice> {

}
