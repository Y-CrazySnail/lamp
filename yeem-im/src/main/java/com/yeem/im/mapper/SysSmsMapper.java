package com.yeem.im.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.im.entity.SysSMS;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysSmsMapper extends BaseMapper<SysSMS> {
    List<SysSMS> getTodo();
}
