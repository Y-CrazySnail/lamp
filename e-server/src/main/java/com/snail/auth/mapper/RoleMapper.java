package com.snail.auth.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.snail.auth.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("auth")
public interface RoleMapper extends BaseMapper<Role> {
}
