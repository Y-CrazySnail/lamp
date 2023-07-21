package com.yeem.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            this.strictInsertFill(metaObject, "createUser", String.class, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            this.strictInsertFill(metaObject, "updateUser", String.class, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        } catch (Exception e) {
        }
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "deleteFlag", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            this.strictInsertFill(metaObject, "updateUser", String.class, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        } catch (Exception e) {
        }
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
