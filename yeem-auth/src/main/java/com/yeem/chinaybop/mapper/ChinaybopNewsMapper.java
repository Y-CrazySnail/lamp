package com.yeem.chinaybop.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.chinaybop.entity.ChinaybopNews;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("chinaybop")
public interface ChinaybopNewsMapper extends BaseMapper<ChinaybopNews> {

}
