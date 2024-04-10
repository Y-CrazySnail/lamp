package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneCart;

import java.util.List;

public interface IOneCartService extends IService<OneCart> {
    List<OneCart> listByUserId(Long userId);

    List<OneCart> listByUserIdAndStoreId(Long userId, Long storeId);

    OneCart getByIdWithOther(Long id);

    List<OneCart> saveForWechat(OneCart cart);

    List<OneCart> clear(OneCart cart);

}
