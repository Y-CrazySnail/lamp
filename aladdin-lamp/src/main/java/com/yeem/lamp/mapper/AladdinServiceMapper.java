package com.yeem.lamp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.entity.AladdinService;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AladdinServiceMapper extends BaseMapper<AladdinService> {
    IPage<AladdinService> selectPages(IPage<AladdinService> page);
}
