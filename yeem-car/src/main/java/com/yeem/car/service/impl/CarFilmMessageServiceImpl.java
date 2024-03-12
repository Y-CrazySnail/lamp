package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car.entity.CarFilmMessage;
import com.yeem.car.mapper.CarFilmMessageMapper;
import com.yeem.car.service.ICarFilmMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.StrUtil;

import java.util.List;

@Service
public class CarFilmMessageServiceImpl extends ServiceImpl<CarFilmMessageMapper, CarFilmMessage> implements ICarFilmMessageService {

    @Autowired
    private CarFilmMessageMapper carFilmMessageMapper;

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public List<CarFilmMessage> list(String productNo, String sendStatus, String name) {
        QueryWrapper<CarFilmMessage> carFilmMessageQueryWrapper = new QueryWrapper<>();
        if (!StrUtil.isEmpty(productNo)) {
            carFilmMessageQueryWrapper.eq("product_no", productNo);
        }
        if (!StrUtil.isEmpty(sendStatus)) {
            carFilmMessageQueryWrapper.eq("send_status", sendStatus);
        }
        if (!StrUtil.isEmpty(name)) {
            carFilmMessageQueryWrapper.like("name", name);
        }
        carFilmMessageQueryWrapper.eq("delete_flag", 0);
        return carFilmMessageMapper.selectList(carFilmMessageQueryWrapper);
    }

    @Override
    public IPage<CarFilmMessage> pages(int current, int size, String productNo, String sendStatus, String name) {
        QueryWrapper<CarFilmMessage> carFilmMessageQueryWrapper = new QueryWrapper<>();
        if (!StrUtil.isEmpty(productNo)) {
            carFilmMessageQueryWrapper.eq("product_no", productNo);
        }
        if (!StrUtil.isEmpty(sendStatus)) {
            carFilmMessageQueryWrapper.eq("send_status", sendStatus);
        }
        if (!StrUtil.isEmpty(name)) {
            carFilmMessageQueryWrapper.like("name", name);
        }
        Page<CarFilmMessage> page = new Page<>(current, size);
        return carFilmMessageMapper.selectPage(page, carFilmMessageQueryWrapper);
    }

    @Override
    public CarFilmMessage getById(Long id) {
        return carFilmMessageMapper.selectOne(new QueryWrapper<CarFilmMessage>().eq("delete_flag", 0).eq("id", id));
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void remove(CarFilmMessage carFilmMessage) {
        carFilmMessage.setDeleteFlag(true);
        carFilmMessageMapper.updateById(carFilmMessage);
        throw new RuntimeException("???");
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean save(CarFilmMessage carFilmMessage) {
        return SqlHelper.retBool(carFilmMessageMapper.insert(carFilmMessage));
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(CarFilmMessage carFilmMessage) {
        carFilmMessageMapper.updateById(carFilmMessage);
    }
}
