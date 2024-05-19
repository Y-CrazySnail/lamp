package com.yeem.game.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.game.entity.GameDataRecord;
import com.yeem.game.mapper.GameDataRecordMapper;
import com.yeem.game.service.IGameDataRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GameDataRecordServiceImpl extends ServiceImpl<GameDataRecordMapper, GameDataRecord> implements IGameDataRecordService {

    @Autowired
    private GameDataRecordMapper mapper;

}
