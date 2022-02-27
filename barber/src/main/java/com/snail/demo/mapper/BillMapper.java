package com.snail.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.demo.entity.Bill;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BillMapper extends BaseMapper<Bill> {
}
