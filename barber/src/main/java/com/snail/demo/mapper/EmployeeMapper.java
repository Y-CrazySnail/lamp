package com.snail.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.demo.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
