package com.yeem.chinaybop.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.chinaybop.entity.ChinaybopMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("chinaybop")
public interface ChinaybopMessageMapper extends BaseMapper<ChinaybopMessage> {

}
