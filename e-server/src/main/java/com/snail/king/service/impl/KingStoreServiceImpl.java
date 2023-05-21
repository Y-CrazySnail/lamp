package com.snail.king.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.king.entity.KingStore;
import com.snail.king.mapper.KingStoreMapper;
import com.snail.king.service.IKingStoreService;
import org.springframework.stereotype.Service;

@Service
public class KingStoreServiceImpl extends ServiceImpl<KingStoreMapper, KingStore> implements IKingStoreService {

}
