package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.chinaybop.entity.BaseEntity;
import com.snail.conreoller.BaseController;
import com.snail.entity.CommandConfig;
import com.snail.entity.CommandRecord;
import com.snail.service.ICommandRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/command_config")
public class CommandConfigController extends BaseController<CommandConfig> {

    @Autowired
    private ICommandRecordService commandService;

    @GetMapping("get")
    public ResponseEntity<Object> get(@RequestParam("ip") String ip) {
        CommandRecord commandRecord = commandService.get(ip);
        return ResponseEntity.ok(commandRecord);
    }

    @GetMapping("pageByCondition")
    public ResponseEntity<Object> getPage(Integer current,
                                          Integer size,
                                          QueryWrapper<CommandConfig> queryWrapper) {
        queryWrapper.orderByDesc(String.valueOf(BaseEntity.BaseField.ID));
        return super.getPage(current, size, queryWrapper);
    }
}
