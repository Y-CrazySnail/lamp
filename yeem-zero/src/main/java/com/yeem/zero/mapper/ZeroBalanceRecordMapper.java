package com.yeem.zero.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.zero.entity.ZeroBalanceRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("zero")
public interface ZeroBalanceRecordMapper extends BaseMapper<ZeroBalanceRecord> {
}
