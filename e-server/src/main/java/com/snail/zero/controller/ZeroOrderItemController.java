package com.snail.zero.controller;

import com.snail.conreoller.BaseController;
import com.snail.zero.entity.ZeroOrderItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zero-order-item")
public class ZeroOrderItemController extends BaseController<ZeroOrderItem> {

}
