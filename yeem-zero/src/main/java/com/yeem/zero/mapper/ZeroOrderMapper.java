package com.yeem.zero.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.zero.entity.ZeroOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@DS("zero")
public interface ZeroOrderMapper extends BaseMapper<ZeroOrder> {
    List<ZeroOrder> distribution(@Param("userId") Long userId, @Param("nickName") String nickName);
}
