package com.snail.zero.controller;

import com.snail.conreoller.BaseController;
import com.snail.zero.entity.ZeroOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zero-order")
public class ZeroOrderController extends BaseController<ZeroOrder> {

}
