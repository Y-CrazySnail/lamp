package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car.entity.CarFilmProduct;
import com.yeem.car.entity.CarFilmQuality;
import com.yeem.car.entity.CarFilmTenant;
import com.yeem.car.mapper.CarFilmQualityMapper;
import com.yeem.car.service.ICarFilmProductService;
import com.yeem.car.service.ICarFilmTenantService;
import com.yeem.car.service.ICarFilmQualityService;
import com.yeem.im.dto.SysMailSendDTO;
import com.yeem.im.dto.SysSMSSendDTO;
import com.yeem.im.entity.SysMail;
import com.yeem.im.service.ISysIMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarFilmQualityServiceImpl extends ServiceImpl<CarFilmQualityMapper, CarFilmQuality> implements ICarFilmQualityService {

    @Autowired
    private CarFilmQualityMapper carFilmQualityMapper;

    @Autowired
    private ISysIMService sysIMService;

    @Autowired
    private ICarFilmTenantService carFilmTenantService;
    @Autowired
    private ICarFilmProductService carFilmProductService;

    @Override
    public List<CarFilmQuality> list(String name, String productNo, String phone, String qualityCardNo, String plateNo, String vin, String likeName, String likePhone, String likeQualityCardNo, String likePlateNo, String likeVin) {
        QueryWrapper<CarFilmQuality> wrapper = new QueryWrapper<>();
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        wrapper.in("product_no", carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()));
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(phone)) {
            wrapper.eq("phone", phone);
        }
        if (!StringUtils.isEmpty(qualityCardNo)) {
            wrapper.eq("quality_card_no", qualityCardNo);
        }
        if (!StringUtils.isEmpty(plateNo)) {
            wrapper.eq("plate_no", plateNo);
        }
        if (!StringUtils.isEmpty(vin)) {
            wrapper.eq("vin", vin);
        }
        if (!StringUtils.isEmpty(likeName)) {
            wrapper.like("name", likeName);
        }
        if (!StringUtils.isEmpty(likePhone)) {
            wrapper.like("phone", likePhone);
        }
        if (!StringUtils.isEmpty(likeQualityCardNo)) {
            wrapper.like("quality_card_no", likeQualityCardNo);
        }
        if (!StringUtils.isEmpty(likePlateNo)) {
            wrapper.like("plate_no", plateNo);
        }
        if (!StringUtils.isEmpty(likeVin)) {
            wrapper.like("vin", likeVin);
        }
        wrapper.eq("delete_flag", 0);
        return carFilmQualityMapper.selectList(wrapper);
    }

    @Override
    public IPage<CarFilmQuality> pages(int current, int size, String name, String productNo, String phone, String qualityCardNo, String plateNo, String vin) {
        QueryWrapper<CarFilmQuality> wrapper = new QueryWrapper<>();
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        wrapper.in("product_no", carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()));
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(phone)) {
            wrapper.like("phone", phone);
        }
        if (!StringUtils.isEmpty(qualityCardNo)) {
            wrapper.like("quality_card_no", qualityCardNo);
        }
        if (!StringUtils.isEmpty(plateNo)) {
            wrapper.like("plate_no", plateNo);
        }
        if (!StringUtils.isEmpty(vin)) {
            wrapper.like("vin", vin);
        }
        wrapper.eq("delete_flag", 0);
        wrapper.orderByAsc("approve_flag");
        wrapper.orderByDesc("id");
        Page<CarFilmQuality> page = new Page<>(current, size);
        return carFilmQualityMapper.selectPage(page, wrapper);
    }

    @Override
    public CarFilmQuality getById(Long id) {
        return carFilmQualityMapper.selectOne(new QueryWrapper<CarFilmQuality>().eq("delete_flag", 0).eq("id", id));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void remove(CarFilmQuality carFilmQuality) {
        CarFilmQuality getOneCarFilmQuality = carFilmQualityMapper.selectById(carFilmQuality.getId());
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(getOneCarFilmQuality.getProductNo())) {
            throw new RuntimeException("User does not have permission: illegal deletion!");
        }
        carFilmQuality.setDeleteFlag(true);
        carFilmQualityMapper.updateById(carFilmQuality);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(CarFilmQuality carFilmQuality) {
        return super.save(carFilmQuality);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(CarFilmQuality carFilmQuality) {
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(carFilmQuality.getProductNo())) {
            throw new RuntimeException("User not authorized: illegal modification");
        }
        carFilmQualityMapper.updateById(carFilmQuality);
    }

    @Override
    public List<CarFilmQuality> getQualityInfo(String productNo, String queryKey) {
        QueryWrapper<CarFilmQuality> carFilmQualityQueryWrapper = new QueryWrapper<>();
        carFilmQualityQueryWrapper.eq("product_no", productNo);
        carFilmQualityQueryWrapper.eq("approve_flag", "1");
        carFilmQualityQueryWrapper.and(wrapper ->
                wrapper.eq("phone", queryKey).or().eq("vin", queryKey).or().eq("quality_card_no", queryKey)
        );
        List<CarFilmQuality> carFilmQualityList = carFilmQualityMapper.selectList(carFilmQualityQueryWrapper);
        carFilmQualityList.forEach(CarFilmQuality::setState);
        carFilmQualityList.forEach(carFilmQuality -> {
            QueryWrapper<CarFilmProduct> carFilmProductQueryWrapper = new QueryWrapper<>();
            carFilmProductQueryWrapper.eq("product_level_no", carFilmQuality.getProductLevelNo());
            CarFilmProduct carFilmProduct = carFilmProductService.getOne(carFilmProductQueryWrapper);
            carFilmQuality.setProductType(carFilmProduct.getProductType());
        });
        return carFilmQualityList;
    }
}
