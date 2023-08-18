package com.yeem.common.im.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.common.im.entity.SysSms;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysSmsMapper extends BaseMapper<SysSms> {
    public List<SysSms> getTodo();
}
