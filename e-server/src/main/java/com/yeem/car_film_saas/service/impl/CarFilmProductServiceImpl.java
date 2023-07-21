package com.yeem.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car_film_saas.entity.CarFilmProduct;
import com.yeem.car_film_saas.mapper.CarFilmProductMapper;
import com.yeem.car_film_saas.service.ICarFilmProductService;
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
    public List<CarFilmProduct> list(String productNo, String productName, String companyName, String companyNo, String managerName, String managerPhone ,String  miniProgramFlag,String officialWebsiteFlag) {
        QueryWrapper<CarFilmProduct> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(productName)) {
            wrapper.like("product_name", productName);
        }
        if (!StringUtils.isEmpty(companyName)) {
            wrapper.like("company_name", companyName);
        }
        if (!StringUtils.isEmpty(companyNo)) {
            wrapper.like("company_no", companyNo);
        }
        if (!StringUtils.isEmpty(managerName)) {
            wrapper.like("manager_name", managerName);
        }
        if (!StringUtils.isEmpty(managerPhone)) {
            wrapper.like("manager_phone", managerPhone);
        }
        if (!StringUtils.isEmpty(miniProgramFlag)) {
            wrapper.eq("mini_program_flag", miniProgramFlag);
        }
        if (!StringUtils.isEmpty(officialWebsiteFlag)) {
            wrapper.eq("official_website_flag", officialWebsiteFlag);
        }    wrapper.eq("delete_flag", 0);
        return carFilmProductMapper.selectList(wrapper);
    }

    /**
     * 分页查询所有不被软删除的数据 外加模糊查询
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public IPage<CarFilmProduct> pages(int current, int size, String productNo, String productName, String companyName, String companyNo, String managerName, String managerPhone, String miniProgramFlag, String officialWebsiteFlag) {
        QueryWrapper<CarFilmProduct> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (!StringUtils.isEmpty(productName)) {
            wrapper.like("product_name", productName);
        }
        if (!StringUtils.isEmpty(companyName)) {
            wrapper.like("company_name", companyName);
        }
        if (!StringUtils.isEmpty(companyNo)) {
            wrapper.like("company_no", companyNo);
        }
        if (!StringUtils.isEmpty(managerName)) {
            wrapper.like("manager_name", managerName);
        }
        if (!StringUtils.isEmpty(managerPhone)) {
            wrapper.like("manager_phone", managerPhone);
        }
        if (!StringUtils.isEmpty(miniProgramFlag)) {
            wrapper.eq("mini_program_flag", miniProgramFlag);
        }
        if (!StringUtils.isEmpty(officialWebsiteFlag)) {
            wrapper.eq("official_website_flag", officialWebsiteFlag);
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
        return carFilmProductMapper.selectOne(new QueryWrapper<CarFilmProduct>().eq("delete_flag", 0).eq("id",id));
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
