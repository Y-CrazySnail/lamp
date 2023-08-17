package com.yeem.common.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.common.im.entity.SysMail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMailMapper extends BaseMapper<SysMail> {
   List<SysMail> getTodoMail();
}
