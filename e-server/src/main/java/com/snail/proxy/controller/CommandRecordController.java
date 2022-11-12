package com.snail.proxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.entity.BaseEntity;
import com.snail.conreoller.BaseController;
import com.snail.proxy.entity.CommandRecord;
import com.snail.proxy.service.ICommandRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/command_record")
public class CommandRecordController extends BaseController<CommandRecord> {

    @Autowired
    private ICommandRecordService commandRecordService;

    @GetMapping("get")
    public ResponseEntity<Object> get(@RequestParam("ip") String ip) {
        CommandRecord commandRecord = commandRecordService.get(ip);
        return ResponseEntity.ok(commandRecord);
    }

    @GetMapping("pageByCondition")
    public ResponseEntity<Object> getPage(Integer current,
                                          Integer size,
                                          QueryWrapper<CommandRecord> queryWrapper) {
        queryWrapper.orderByDesc(String.valueOf(BaseEntity.BaseField.ID));
        return super.getPage(current, size, queryWrapper);
    }

    @PutMapping("finish")
    public ResponseEntity<Object> update(@RequestBody CommandRecord entity) {
        entity.setExecuteEndTime(LocalDateTime.now());
        return super.update(entity);
    }
}
