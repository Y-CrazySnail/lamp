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
    List<ZeroUserExtra> distribution(@Param("username") String username,
                                     @Param("nickName") String nickName);

    void addBalance(@Param("username") String username,
                    @Param("amount") BigDecimal amount);

    void subtractBalance(@Param("username") String username,
                         @Param("amount") BigDecimal amount);

    void addTodoBalance(@Param("username") String username,
                        @Param("amount") BigDecimal amount);

    void subtractTodoBalance(@Param("username") String username,
                             @Param("amount") BigDecimal amount);
}
