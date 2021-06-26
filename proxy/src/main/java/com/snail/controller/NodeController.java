package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Node;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/node")
public class NodeController extends BaseController<Node> {

}
