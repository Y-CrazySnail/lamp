package com.snail.conreoller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.entity.BaseEntity;
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
    public ResponseEntity<Object> getById(Long id) {
        T entity = service.getById(id);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("getAll")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("page")
    public ResponseEntity<Object> getPage(Integer current,
                                          Integer size,
                                          String order,
                                          String column1,
                                          String value1,
                                          String column2,
                                          String value2,
                                          String column3,
                                          String value3,
                                          QueryWrapper<T> queryWrapper) {
        if (StringUtils.isEmpty(current)) {
            current = 1;
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        if (!StringUtils.isEmpty(order)) {
            queryWrapper.orderByAsc(order);
        }
        if (!StringUtils.isEmpty(column1) && !StringUtils.isEmpty(value1)) {
            queryWrapper.like(column1, value1);
        }
        if (!StringUtils.isEmpty(column2) && !StringUtils.isEmpty(value2)) {
            queryWrapper.like(column2, value2);
        }
        if (!StringUtils.isEmpty(column3) && !StringUtils.isEmpty(value3)) {
            queryWrapper.like(column3, value3);
        }
        IPage<T> page = new Page<>(current, size);
        return ResponseEntity.ok(service.page(page, queryWrapper));
    }

    @PostMapping("baseSave")
    public ResponseEntity<Object> save(@RequestBody T entity) {
        service.save(entity);
        return ResponseEntity.ok("保存成功");
    }

    @PutMapping("baseUpdate")
    public ResponseEntity<Object> update(@RequestBody T entity) {
        service.updateById(entity);
        return ResponseEntity.ok("更新成功");
    }

    @DeleteMapping("baseDelete")
    public ResponseEntity<Object> delete(@RequestBody T entity) {
        service.removeById(entity.getId());
        return ResponseEntity.ok("删除成功");
    }

}
