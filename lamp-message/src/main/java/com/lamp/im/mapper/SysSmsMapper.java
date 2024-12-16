package com.lamp.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lamp.im.entity.SysSMS;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysSmsMapper extends BaseMapper<SysSMS> {
    List<SysSMS> getTodo();
}
