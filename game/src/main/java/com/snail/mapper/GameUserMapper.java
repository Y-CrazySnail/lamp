package com.snail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.entity.GameUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameUserMapper extends BaseMapper<GameUser> {
}
