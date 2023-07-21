package com.yeem.zero.controller;

import com.yeem.conreoller.BaseController;
import com.yeem.zero.entity.ZeroOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zero-order")
public class ZeroOrderController extends BaseController<ZeroOrder> {

}
