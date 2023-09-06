package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroUserExtra;

import java.math.BigDecimal;
import java.util.List;

public interface IZeroUserExtraService extends IService<ZeroUserExtra> {
    ZeroUserExtra get(String username);
    ZeroUserExtra getByUserId(Long userId);
    ZeroUserExtra update(ZeroUserExtra zeroUserExtra);
    List<ZeroUserExtra> distribution(String username, String nickName);
    Integer getDirectReferrerUserCount(String username);
    Integer getIndirectReferrerUserCount(String username);
    void addBalance(String username, BigDecimal amount);
    void subtractBalance(String username, BigDecimal amount);
    void addTodoBalance(String username, BigDecimal amount);
    void subtractTodoBalance(String username, BigDecimal amount);
}
