package com.yeem.zero.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.mapper.ZeroOrderMapper;
import com.yeem.zero.service.IZeroOrderService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class ZeroOrderServiceImpl extends ServiceImpl<ZeroOrderMapper, ZeroOrder> implements IZeroOrderService {

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    @Override
    public ZeroOrder order(ZeroOrder zeroOrder) {
        String username = OauthUtils.getUsername();
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(username);
        if (StringUtils.isEmpty(zeroUserExtra)) {
            throw new RuntimeException("user info is null when save cart");
        }
        zeroOrder.setUserId(zeroUserExtra.getUserId());
        return null;
    }
}
