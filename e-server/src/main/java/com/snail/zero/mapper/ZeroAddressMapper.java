package com.snail.zero.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.zero.entity.ZeroAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@DS("zero")
public interface ZeroAddressMapper extends BaseMapper<ZeroAddress> {
    List<ZeroAddress> listByUsername(@Param("username") String username);
}
