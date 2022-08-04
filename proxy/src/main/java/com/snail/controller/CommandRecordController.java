package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.chinaybop.entity.BaseEntity;
import com.snail.conreoller.BaseController;
import com.snail.entity.CommandRecord;
import com.snail.service.ICommandConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/command_record")
public class CommandRecordController extends BaseController<CommandRecord> {

    @Autowired
    private ICommandConfigService commandConfigService;

    @GetMapping("get")
    @ApiOperation(value = "查询未执行命令接口")
    public ResponseEntity<Object> get(@RequestParam("ip") String ip) {
//        CommandRecord commandRecord = commandConfigService.get(ip);
        return ResponseEntity.ok(null);
    }

    @GetMapping("pageByCondition")
    @ApiOperation(value = "分页查询接口")
    public ResponseEntity<Object> getPage(Integer current,
                                          Integer size,
                                          QueryWrapper<CommandRecord> queryWrapper) {
        queryWrapper.orderByDesc(String.valueOf(BaseEntity.BaseField.ID));
        return super.getPage(current, size, queryWrapper);
    }
}
