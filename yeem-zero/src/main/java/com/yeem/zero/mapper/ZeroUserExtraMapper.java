package com.yeem.zero.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.zero.entity.ZeroUserExtra;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@DS("zero")
public interface ZeroUserExtraMapper extends BaseMapper<ZeroUserExtra> {
    List<ZeroUserExtra> distribution(@Param("username") String username,
                                     @Param("nickName") String nickName);
}
