package com.yeem.lamp.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.lamp.entity.ShareNode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("proxy")
public interface ShareNodeMapper extends BaseMapper<ShareNode> {

}
