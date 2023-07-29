package com.yeem.king.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.king.entity.KingStore;
import com.yeem.king.mapper.KingStoreMapper;
import com.yeem.king.service.IKingStoreService;
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
