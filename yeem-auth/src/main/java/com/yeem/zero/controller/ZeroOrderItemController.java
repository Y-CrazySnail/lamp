package com.yeem.zero.controller;

import com.yeem.conreoller.BaseController;
import com.yeem.zero.entity.ZeroOrderItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zero-order-item")
public class ZeroOrderItemController extends BaseController<ZeroOrderItem> {

}
