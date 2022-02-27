package com.snail.demo.controller;

import com.snail.conreoller.BaseController;
import com.snail.demo.entity.ChildBill;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo-child-bill")
public class ChildBillController extends BaseController<ChildBill> {

}
