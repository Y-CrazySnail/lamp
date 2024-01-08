package com.yeem.zero.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.zero.entity.ZeroUserExtra;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@DS("zero")
public interface ZeroUserExtraMapper extends BaseMapper<ZeroUserExtra> {
    List<ZeroUserExtra> distribution(@Param("userId") Long userId,
                                     @Param("nickName") String nickName);

    void addBalance(@Param("userId") Long userId,
                    @Param("amount") BigDecimal amount);

    void subtractBalance(@Param("userId") Long userId,
                         @Param("amount") BigDecimal amount);

    void addTodoBalance(@Param("userId") Long userId,
                        @Param("amount") BigDecimal amount);

    void subtractTodoBalance(@Param("userId") Long userId,
                             @Param("amount") BigDecimal amount);

    int distributionUserCountByIdCardNo(@Param("idCardNo") String idCardNo);
}
