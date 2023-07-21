package com.yeem.zero.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.zero.entity.ZeroProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@DS("zero")
public interface ZeroProductMapper extends BaseMapper<ZeroProduct> {
    /**
     * 根据类别ID获取商品信息列表
     *
     * @param categoryId 类别ID
     * @return 商品信息列表
     */
    List<ZeroProduct> selectByCategoryId(Long categoryId);
}
