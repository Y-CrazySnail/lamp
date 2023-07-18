package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.snail.car_film_saas.entity.CarFilmProduct;
import com.snail.car_film_saas.mapper.CarFilmProductMapper;
import com.snail.car_film_saas.service.ICarFilmProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CarFilmProductServiceImpl extends ServiceImpl<CarFilmProductMapper, CarFilmProduct> implements ICarFilmProductService {

    @Autowired
    private CarFilmProductMapper carFilmProductMapper;

    /**
     * 查询不被软删除的数据
     *
     * @param
     * @return
     */
    @Override
    public List<CarFilmProduct> list(CarFilmProduct carFilmProduct) {
        QueryWrapper<CarFilmProduct> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(carFilmProduct.getProductNo())) {
            wrapper.like("product_no", carFilmProduct.getProductNo());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getProductName())) {
            wrapper.like("product_name", carFilmProduct.getProductName());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getCompanyName())) {
            wrapper.like("company_name", carFilmProduct.getCompanyName());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getCompanyNo())) {
            wrapper.like("company_no", carFilmProduct.getCompanyNo());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getManagerName())) {
            wrapper.like("manager_name", carFilmProduct.getManagerName());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getManagerPhone())) {
            wrapper.like("manager_phone", carFilmProduct.getManagerPhone());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getMiniProgramFlag())) {
            wrapper.eq("mini_program_flag", carFilmProduct.getMiniProgramFlag());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getOfficialWebsiteFlag())) {
            wrapper.eq("official_website_flag", carFilmProduct.getOfficialWebsiteFlag());
        }
        QueryWrapper<CarFilmProduct> deleteFlag = wrapper.eq("delete_flag", 0);
        return carFilmProductMapper.selectList(deleteFlag);
    }

    /**
     * 分页查询所有不被软删除的数据 外加模糊查询
     *
     * @param current
     * @param size
     * @param carFilmProduct
     * @return
     */
    @Override
    public IPage<CarFilmProduct> pages(int current, int size, CarFilmProduct carFilmProduct) {
        QueryWrapper<CarFilmProduct> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(carFilmProduct.getProductNo())) {
            wrapper.like("product_no", carFilmProduct.getProductNo());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getProductName())) {
            wrapper.like("product_name", carFilmProduct.getProductName());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getCompanyName())) {
            wrapper.like("company_name", carFilmProduct.getCompanyName());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getCompanyNo())) {
            wrapper.like("company_no", carFilmProduct.getCompanyNo());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getManagerName())) {
            wrapper.like("manager_name", carFilmProduct.getManagerName());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getManagerPhone())) {
            wrapper.like("manager_phone", carFilmProduct.getManagerPhone());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getMiniProgramFlag())) {
            wrapper.eq("mini_program_flag", carFilmProduct.getMiniProgramFlag());
        }
        if (!StringUtils.isEmpty(carFilmProduct.getOfficialWebsiteFlag())) {
            wrapper.eq("official_website_flag", carFilmProduct.getOfficialWebsiteFlag());
        }
        wrapper.eq("delete_flag", 0);
        Page<CarFilmProduct> page = new Page<>(current, size);
        return carFilmProductMapper.selectPage(page, wrapper);
    }

    /**
     * 按id查询 不可以查询到软删除的用户
     *
     * @param id id
     * @return
     */
    @Override
    public CarFilmProduct getById(Long id) {
        return carFilmProductMapper.selectOne(new QueryWrapper<CarFilmProduct>().eq("delete_flag", 0));
    }

    /**
     * 软删除
     *
     * @param carFilmProduct
     */
    @Override
    public void remove(CarFilmProduct carFilmProduct) {
        carFilmProduct.setDeleteFlag(true);
        carFilmProductMapper.updateById(carFilmProduct);
    }

    /**
     * 增加商品
     *
     * @param carFilmProduct
     */
    @Override
    public boolean save(CarFilmProduct carFilmProduct) {
        return SqlHelper.retBool(carFilmProductMapper.insert(carFilmProduct));
    }

    /**
     * 更改商品
     *
     * @param carFilmProduct
     */
    @Override
    public void update(CarFilmProduct carFilmProduct) {
        carFilmProductMapper.updateById(carFilmProduct);
    }
}
