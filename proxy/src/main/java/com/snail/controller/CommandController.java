package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.chinaybop.entity.BaseEntity;
import com.snail.conreoller.BaseController;
import com.snail.entity.Command;
import com.snail.service.ICommandService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/command")
public class CommandController extends BaseController<Command> {

    @Autowired
    private ICommandService commandService;

    @GetMapping("get")
    @ApiOperation(value = "查询未执行命令接口")
    public ResponseEntity<Object> get(@RequestParam("ip") String ip) {
        Command command = commandService.get(ip);
        return ResponseEntity.ok(command);
    }

    @GetMapping("pageByCondition")
    @ApiOperation(value = "分页查询接口")
    public ResponseEntity<Object> getPage(Integer current,
                                          Integer size,
                                          QueryWrapper<Command> queryWrapper) {
        queryWrapper.orderByDesc(String.valueOf(BaseEntity.BaseField.ID));
        return super.getPage(current, size, queryWrapper);
    }
}
