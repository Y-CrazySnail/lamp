package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.CarFilmTenant;
import com.yeem.car.entity.CarFilmUser;
import com.yeem.car.mapper.CarFilmUserMapper;
import com.yeem.car.service.ICarFilmTenantService;
import com.yeem.car.service.ICarFilmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarFilmUserServiceImpl extends ServiceImpl<CarFilmUserMapper, CarFilmUser> implements ICarFilmUserService {

    @Autowired
    private ICarFilmTenantService carFilmTenantService;
    @Autowired
    private CarFilmUserMapper carFilmUserMapper;

    @Override
    public IPage<CarFilmUser> pages(int current, int size, String productNo, String nickName, String phone) {
        Page<CarFilmUser> page = new Page<>(current, size);
        QueryWrapper<CarFilmUser> wrapper = new QueryWrapper<>();
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        wrapper.in("product_no", carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()));
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.like("phone", phone);
        }
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.like("nick_name", nickName);
        }
        wrapper.eq("delete_flag", 0);
        return carFilmUserMapper.selectPage(page, wrapper);
    }
}
