package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.snail.car_film_saas.entity.CarFilmMessage;
import com.snail.car_film_saas.mapper.CarFilmMessageMapper;
import com.snail.car_film_saas.service.ICarFilmMessageService;
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
     * @param carFilmMessage
     * @return
     */
    @Override
    public List<CarFilmMessage> list(CarFilmMessage carFilmMessage) {
        QueryWrapper<CarFilmMessage> carFilmMessageQueryWrapper=new QueryWrapper<>();
        if (StringUtils.isEmpty(carFilmMessage.getProductNo())){
            carFilmMessageQueryWrapper.eq("product_no",carFilmMessage.getProductNo());
        }
        if (StringUtils.isEmpty(carFilmMessage.getSendStatus())){
            carFilmMessageQueryWrapper.eq("send_status",carFilmMessage.getSendStatus());
        }
        if (StringUtils.isEmpty(carFilmMessage.getName())){
            carFilmMessageQueryWrapper.like("name",carFilmMessage.getName());
        }
        carFilmMessageQueryWrapper.eq("delete_flag", 0);
        return carFilmMessageMapper.selectList(carFilmMessageQueryWrapper);
    }

    @Override
    public IPage<CarFilmMessage> pages(int current, int size, CarFilmMessage carFilmMessage) {
        QueryWrapper<CarFilmMessage> carFilmMessageQueryWrapper=new QueryWrapper<>();
        if (StringUtils.isEmpty(carFilmMessage.getProductNo())){
            carFilmMessageQueryWrapper.eq("product_no",carFilmMessage.getProductNo());
        }
        if (StringUtils.isEmpty(carFilmMessage.getSendStatus())){
            carFilmMessageQueryWrapper.eq("send_status",carFilmMessage.getSendStatus());
        }
        if (StringUtils.isEmpty(carFilmMessage.getName())){
            carFilmMessageQueryWrapper.like("name",carFilmMessage.getName());
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
