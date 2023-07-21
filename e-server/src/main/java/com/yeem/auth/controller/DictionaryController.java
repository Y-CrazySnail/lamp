package com.yeem.auth.controller;

import com.yeem.conreoller.BaseController;
import com.yeem.auth.entity.Dictionary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController<Dictionary> {

}
