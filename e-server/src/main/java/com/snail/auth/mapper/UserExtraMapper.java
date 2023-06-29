package com.snail.auth.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.auth.entity.UserExtra;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("auth")
public interface UserExtraMapper extends BaseMapper<UserExtra> {
}
