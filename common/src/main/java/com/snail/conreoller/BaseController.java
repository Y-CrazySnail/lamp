package com.snail.conreoller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.entity.BaseEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

public class BaseController<T extends BaseEntity> {

    @Autowired
    private IService<T> service;

    @GetMapping("getById")
    @ApiOperation(value = "根据ID查询接口")
    public ResponseEntity<Object> getById(Long id) {
        T entity = service.getById(id);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("getAll")
    @ApiOperation(value = "查询全部接口")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("page")
    @ApiOperation(value = "分页查询接口")
    public ResponseEntity<Object> getPage(Integer current,
                                          Integer size,
                                          QueryWrapper<T> queryWrapper) {
        if (StringUtils.isEmpty(current)) {
            current = 1;
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        IPage<T> page = new Page<>(current, size);
        return ResponseEntity.ok(service.page(page, queryWrapper));
    }

    @PostMapping("baseSave")
    @ApiOperation(value = "保存接口")
    public ResponseEntity<Object> save(@RequestBody T entity) {
        service.save(entity);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("baseUpdate")
    @ApiOperation(value = "更新接口")
    public ResponseEntity<Object> update(@RequestBody T entity) {
        service.updateById(entity);
        return ResponseEntity.ok("更新成功");
    }

    @DeleteMapping("baseDelete")
    @ApiOperation(value = "删除接口")
    public ResponseEntity<Object> delete(@RequestBody T entity) {
        service.removeById(entity.getId());
        return ResponseEntity.ok("删除成功");
    }

}
