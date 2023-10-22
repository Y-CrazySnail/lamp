package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroUserExtra;

import java.math.BigDecimal;
import java.util.List;

public interface IZeroUserExtraService extends IService<ZeroUserExtra> {
    ZeroUserExtra get(Long userId);
    ZeroUserExtra getByUserId(Long userId);
    ZeroUserExtra getByWechatOpenId(String openId);
    List<ZeroUserExtra> distribution(Long userId, String nickName);
    Integer getDirectReferrerUserCount(Long userId);
    Integer getIndirectReferrerUserCount(Long userId);
    void addBalance(Long userId, BigDecimal amount);
    void subtractBalance(Long userId, BigDecimal amount);
    void addTodoBalance(Long userId, BigDecimal amount);
    void subtractTodoBalance(Long userId, BigDecimal amount);
}
