package com.yeem.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car_film_saas.entity.CarFilmStore;
import com.yeem.car_film_saas.mapper.CarFilmStoreMapper;
import com.yeem.car_film_saas.service.ICarFilmStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarFilmStoreServiceImpl extends ServiceImpl<CarFilmStoreMapper, CarFilmStore> implements ICarFilmStoreService {

    @Autowired
    private CarFilmStoreMapper carFilmStoreMapper;

    @Override
    public List<CarFilmStore> list(String productNo, String name, String province, String city, String county, String contactName, String contactPhone) {
        QueryWrapper<CarFilmStore> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(province)) {
            wrapper.eq("province", province);
        }
        if (!StringUtils.isEmpty(city)) {
            wrapper.eq("city", city);
        }
        if (!StringUtils.isEmpty(county)) {
            wrapper.eq("county", county);
        }
        if (!StringUtils.isEmpty(contactName)) {
            wrapper.like("contact_name", contactName);
        }
        if (!StringUtils.isEmpty(contactPhone)) {
            wrapper.like("contact_phone", contactPhone);
        }
        wrapper.eq("delete_flag", 0);
        return carFilmStoreMapper.selectList(wrapper);
    }

    @Override
    public IPage<CarFilmStore> pages(int current, int size, String productNo, String name, String province, String city, String county, String contactName, String contactPhone) {
        QueryWrapper<CarFilmStore> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(province)) {
            wrapper.eq("province", province);
        }
        if (!StringUtils.isEmpty(city)) {
            wrapper.eq("city", city);
        }
        if (!StringUtils.isEmpty(county)) {
            wrapper.eq("county", county);
        }
        if (!StringUtils.isEmpty(contactName)) {
            wrapper.like("contact_name", contactName);
        }
        if (!StringUtils.isEmpty(contactPhone)) {
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
        carFilmStore.setDeleteFlag(true);
        carFilmStoreMapper.updateById(carFilmStore);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(CarFilmStore carFilmStore) {
        return SqlHelper.retBool(carFilmStoreMapper.insert(carFilmStore));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(CarFilmStore carFilmStore) {
        carFilmStoreMapper.updateById(carFilmStore);
    }

    /**
     * 输入经纬度查询距离门店
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
