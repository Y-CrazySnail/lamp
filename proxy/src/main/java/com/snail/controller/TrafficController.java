package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Traffic;
import com.snail.service.ITrafficService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/traffic")
public class TrafficController extends BaseController<Traffic> {

}
