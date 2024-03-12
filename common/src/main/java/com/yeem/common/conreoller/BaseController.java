package com.yeem.common.conreoller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;

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
                                          QueryWrapper<T> queryWrapper) {
        if (null == current) {
            current = 1;
        }
        if (null == size) {
            size = 10;
        }
        queryWrapper.orderByDesc(BaseEntity.BaseField.CREATE_TIME.getName());
        IPage<T> page = new Page<>(current, size);
        return ResponseEntity.ok(service.page(page, queryWrapper));
    }

    @PostMapping("baseSave")
    public ResponseEntity<Object> save(@RequestBody T entity) {
        service.save(entity);
        return ResponseEntity.ok(entity);
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
