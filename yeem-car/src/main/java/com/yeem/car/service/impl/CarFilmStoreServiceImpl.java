package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car.entity.CarFilmStore;
import com.yeem.car.entity.CarFilmTenant;
import com.yeem.car.mapper.CarFilmStoreMapper;
import com.yeem.car.service.ICarFilmStoreService;
import com.yeem.car.service.ICarFilmTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarFilmStoreServiceImpl extends ServiceImpl<CarFilmStoreMapper, CarFilmStore> implements ICarFilmStoreService {

    @Autowired
    private CarFilmStoreMapper carFilmStoreMapper;

    @Autowired
    private ICarFilmTenantService carFilmTenantService;

    @Override
    public List<CarFilmStore> list(String productNo, String name, String province, String city, String county, String contactName, String contactPhone) {
        QueryWrapper<CarFilmStore> wrapper = new QueryWrapper<>();
        if (!StrUtil.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StrUtil.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StrUtil.isEmpty(province)) {
            wrapper.eq("province", province);
        }
        if (!StrUtil.isEmpty(city)) {
            wrapper.eq("city", city);
        }
        if (!StrUtil.isEmpty(county)) {
            wrapper.eq("county", county);
        }
        if (!StrUtil.isEmpty(contactName)) {
            wrapper.like("contact_name", contactName);
        }
        if (!StrUtil.isEmpty(contactPhone)) {
            wrapper.like("contact_phone", contactPhone);
        }
        wrapper.eq("delete_flag", 0);
        return carFilmStoreMapper.selectList(wrapper);
    }

    @Override
    public IPage<CarFilmStore> pages(int current, int size, String productNo, String name, String province, String city, String county, String contactName, String contactPhone) {
        QueryWrapper<CarFilmStore> wrapper = new QueryWrapper<>();
        if (!StrUtil.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StrUtil.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StrUtil.isEmpty(province)) {
            wrapper.eq("province", province);
        }
        if (!StrUtil.isEmpty(city)) {
            wrapper.eq("city", city);
        }
        if (!StrUtil.isEmpty(county)) {
            wrapper.eq("county", county);
        }
        if (!StrUtil.isEmpty(contactName)) {
            wrapper.like("contact_name", contactName);
        }
        if (!StrUtil.isEmpty(contactPhone)) {
            wrapper.like("contact_phone", contactPhone);
        }
        wrapper.eq("delete_flag", 0);
        Page<CarFilmStore> page = new Page<>(current, size);
        return carFilmStoreMapper.selectPage(page, wrapper);
    }

    @Override
    public CarFilmStore getById(Long id) {
        return carFilmStoreMapper.selectOne(new QueryWrapper<CarFilmStore>().eq("id", id).eq("delete_flag", 0));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void remove(CarFilmStore carFilmStore) {
        CarFilmStore getOneCarCarFilmStore = carFilmStoreMapper.selectById(carFilmStore.getId());
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(getOneCarCarFilmStore.getProductNo())) {
            throw new RuntimeException("User does not have permission: illegal deletion!");
        }
        carFilmStore.setDeleteFlag(true);
        carFilmStoreMapper.updateById(carFilmStore);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(CarFilmStore carFilmStore) {
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(carFilmStore.getProductNo())) {
            return false;
        }
        return SqlHelper.retBool(carFilmStoreMapper.insert(carFilmStore));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(CarFilmStore carFilmStore) {
        List<CarFilmTenant> carFilmTenantList = carFilmTenantService.listByAuthorizedUsername();
        if (carFilmTenantList.isEmpty() || !carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()).contains(carFilmStore.getProductNo())) {
            throw new RuntimeException("User not authorized: illegal modification");
        }
        carFilmStoreMapper.updateById(carFilmStore);
    }

    /**
     * 输入经纬度查询距离门店
     *
     * @param latitude
     * @param longitude
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public List<CarFilmStore> selectAddress(String latitude, String longitude) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        return carFilmStoreMapper.selectAddress(map);
    }

}
