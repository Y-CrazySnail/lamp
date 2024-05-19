package com.yeem.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.game.entity.GameData;

public interface IGameDataService extends IService<GameData> {
    void deal(String fileContent);
}
