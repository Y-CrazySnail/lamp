package com.snail.king.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.king.entity.KingStore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KingStoreMapper extends BaseMapper<KingStore> {
    List<KingStore> getClose(KingStore kingStore);
}
