package com.snail.king.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.king.entity.KingStore;

import java.util.List;

public interface IKingStoreService extends IService<KingStore> {

    List<KingStore> getClose(KingStore kingStore);

}
