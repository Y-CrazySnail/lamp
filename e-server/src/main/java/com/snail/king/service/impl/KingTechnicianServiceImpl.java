package com.snail.king.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.king.entity.KingTechnician;
import com.snail.king.mapper.KingTechnicianMapper;
import com.snail.king.service.IKingTechnicianService;
import org.springframework.stereotype.Service;

@Service
public class KingTechnicianServiceImpl extends ServiceImpl<KingTechnicianMapper, KingTechnician> implements IKingTechnicianService {

}
