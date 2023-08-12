package com.yeem.car_film_saas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.car_film_saas.entity.SysMail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMailMapper extends BaseMapper<SysMail> {
   List<SysMail> getTodoMail();
}
