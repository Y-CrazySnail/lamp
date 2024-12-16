package com.lamp.infrastructure.persistence.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lamp.infrastructure.persistence.entity.ServiceDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ServiceMapper extends BaseMapper<ServiceDo> {
    IPage<ServiceDo> selectPages(IPage<ServiceDo> page,
                                 @Param("memberId") Long memberId,
                                 @Param("status") String status,
                                 @Param("wechat") String wechat,
                                 @Param("email") String email);
}
