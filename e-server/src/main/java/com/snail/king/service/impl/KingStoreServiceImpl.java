package com.snail.king.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.king.entity.KingStore;
import com.snail.king.mapper.KingStoreMapper;
import com.snail.king.service.IKingStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KingStoreServiceImpl extends ServiceImpl<KingStoreMapper, KingStore> implements IKingStoreService {

    @Autowired
    private KingStoreMapper kingStoreMapper;

    @Override
    public List<KingStore> getClose(KingStore kingStore) {
        return kingStoreMapper.getClose(kingStore);
    }
}
