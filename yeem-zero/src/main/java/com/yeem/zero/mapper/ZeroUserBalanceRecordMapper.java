package com.yeem.zero.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.zero.entity.ZeroUserBalanceRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("zero")
public interface ZeroUserBalanceRecordMapper extends BaseMapper<ZeroUserBalanceRecord> {
}
