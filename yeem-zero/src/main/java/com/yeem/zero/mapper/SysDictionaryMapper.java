package com.yeem.zero.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.zero.entity.SysDictionary;
import com.yeem.zero.entity.ZeroAddress;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("zero")
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {
}
