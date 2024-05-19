package com.yeem.game.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.game.entity.GameData;
import com.yeem.game.entity.GameDataRecord;
import com.yeem.game.mapper.GameDataMapper;
import com.yeem.game.service.IGameDataRecordService;
import com.yeem.game.service.IGameDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class GameDataServiceImpl extends ServiceImpl<GameDataMapper, GameData> implements IGameDataService {

    @Autowired
    private GameDataMapper mapper;
    @Autowired
    private IGameDataRecordService gameDataRecordService;

    @Override
    public void deal(String fileContent) {
        GameData gameData = new GameData();
        gameData.setAllianceName("诸子百家");
        gameData.setUploadTime(new Date());
        mapper.insert(gameData);
        String[] lines = fileContent.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (i != 0) {
                String[] values = lines[i].split(",");
                GameDataRecord record = new GameDataRecord();
                record.setDataId(gameData.getId());
                record.setMemberName(values[0].replace(" ", ""));
                record.setContributionRank(Integer.parseInt(values[1].replace(" ", "")));
                record.setContributionThisWeek(Integer.parseInt(values[2].replace(" ", "")));
                record.setMeritThisWeek(Integer.parseInt(values[3].replace(" ", "")));
                record.setAssistsThisWeek(Integer.parseInt(values[4].replace(" ", "")));
                record.setDonationsThisWeek(Integer.parseInt(values[5].replace(" ", "")));
                record.setTotalContribution(Long.parseLong(values[6].replace(" ", "")));
                record.setTotalMerit(Integer.parseInt(values[7].replace(" ", "")));
                record.setTotalAssists(Integer.parseInt(values[8].replace(" ", "")));
                record.setTotalDonations(Integer.parseInt(values[9].replace(" ", "")));
                record.setInfluence(Integer.parseInt(values[10].replace(" ", "")));
                record.setState(values[11].replace(" ", ""));
                record.setGroupName(values[12].replace(" ", ""));
                gameDataRecordService.save(record);
            }
        }
    }
}
