package com.snail.demo.controller;

import com.snail.conreoller.BaseController;
import com.snail.demo.entity.Employee;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo-employee")
public class EmployeeController extends BaseController<Employee> {

}
