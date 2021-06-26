package com.snail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.entity.Role;
import com.snail.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
