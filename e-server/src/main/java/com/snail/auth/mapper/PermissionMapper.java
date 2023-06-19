package com.snail.auth.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.snail.auth.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@DS("auth")
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> permissionListByUserId(@Param("userId") Long userId);
}
