package com.lamp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lamp.common.entity.SysDictionary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictMapper extends BaseMapper<SysDictionary> {
}
