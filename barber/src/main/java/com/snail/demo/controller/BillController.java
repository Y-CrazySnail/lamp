package com.snail.demo.controller;

import com.snail.conreoller.BaseController;
import com.snail.demo.entity.Bill;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo-bill")
public class BillController extends BaseController<Bill> {

}
