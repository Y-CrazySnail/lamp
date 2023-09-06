package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroFavorite;

import java.util.List;

public interface IZeroFavoriteService extends IService<ZeroFavorite> {
    List<ZeroFavorite> listByUsername(String username);
}
