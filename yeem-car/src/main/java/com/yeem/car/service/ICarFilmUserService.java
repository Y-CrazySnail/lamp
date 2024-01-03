package com.yeem.car.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.entity.CarFilmUser;
import org.springframework.web.bind.annotation.RequestParam;

public interface ICarFilmUserService extends IService<CarFilmUser> {
    IPage<CarFilmUser> pages(int current, int size, String productNo, String nickName, String phone);
}
