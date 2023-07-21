package com.yeem.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car_film_saas.entity.CarFilmMessage;
import com.yeem.car_film_saas.mapper.CarFilmMessageMapper;
import com.yeem.car_film_saas.service.ICarFilmMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CarFilmMessageServiceImpl extends ServiceImpl<CarFilmMessageMapper, CarFilmMessage> implements ICarFilmMessageService {

    @Autowired
    private CarFilmMessageMapper carFilmMessageMapper;

    /**
     * 查询全部
     * @return
     */
    @Override
    public List<CarFilmMessage> list(String productNo,String sendStatus,String name) {
        QueryWrapper<CarFilmMessage> carFilmMessageQueryWrapper=new QueryWrapper<>();
        if (StringUtils.isEmpty(productNo)){
            carFilmMessageQueryWrapper.eq("product_no",productNo);
        }
        if (StringUtils.isEmpty(sendStatus)){
            carFilmMessageQueryWrapper.eq("send_status",sendStatus);
        }
        if (StringUtils.isEmpty(name)){
            carFilmMessageQueryWrapper.like("name",name);
        }
        carFilmMessageQueryWrapper.eq("delete_flag", 0);
        return carFilmMessageMapper.selectList(carFilmMessageQueryWrapper);
    }

    @Override
    public IPage<CarFilmMessage> pages(int current, int size, String productNo,String sendStatus,String name) {
        QueryWrapper<CarFilmMessage> carFilmMessageQueryWrapper=new QueryWrapper<>();
        if (StringUtils.isEmpty(productNo)){
            carFilmMessageQueryWrapper.eq("product_no",productNo);
        }
        if (StringUtils.isEmpty(sendStatus)){
            carFilmMessageQueryWrapper.eq("send_status",sendStatus);
        }
        if (StringUtils.isEmpty(name)){
            carFilmMessageQueryWrapper.like("name",name);
        }
        Page<CarFilmMessage> page=new Page<>(current,size);
        return carFilmMessageMapper.selectPage(page,carFilmMessageQueryWrapper);
    }

    @Override
    public CarFilmMessage getById(Long id) {
        return carFilmMessageMapper.selectOne(new QueryWrapper<CarFilmMessage>().eq("delete_flag", 0).eq("id",id));
    }


    @Override
    public void remove(CarFilmMessage carFilmMessage) {
    carFilmMessage.setDeleteFlag(true);
    carFilmMessageMapper.updateById(carFilmMessage);
    }

    @Override
    public boolean save(CarFilmMessage carFilmMessage) {
       return SqlHelper.retBool(carFilmMessageMapper.insert(carFilmMessage));
    }

    @Override
    public void update(CarFilmMessage carFilmMessage) {
        carFilmMessageMapper.updateById(carFilmMessage);
    }
}
