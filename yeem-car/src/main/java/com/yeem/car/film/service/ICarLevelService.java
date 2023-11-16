package com.yeem.car.film.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.film.entity.BaseCarLevel;

import java.util.List;

public interface ICarLevelService extends IService<BaseCarLevel> {
    /**
     * 传进levelNo 查询  levelName
     *
     * @return
     */
    List<BaseCarLevel> list();

}
