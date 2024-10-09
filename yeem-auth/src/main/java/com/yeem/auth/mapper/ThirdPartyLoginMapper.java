package com.yeem.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.auth.entity.ThirdPartyLogin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ThirdPartyLoginMapper extends BaseMapper<ThirdPartyLogin> {
}
