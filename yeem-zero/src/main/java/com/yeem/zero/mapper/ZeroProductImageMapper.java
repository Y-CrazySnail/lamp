package com.yeem.zero.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.zero.entity.ZeroProductImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@DS("zero")
public interface ZeroProductImageMapper extends BaseMapper<ZeroProductImage> {
    List<ZeroProductImage> selectByProductId(Long productId);
}
