package com.yeem.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.car_film_saas.entity.CarFilmTenant;
import com.yeem.car_film_saas.mapper.CarFilmTenantMapper;
import com.yeem.car_film_saas.service.ICarFilmTenantService;
import com.yeem.common.utils.OauthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CarFilmTenantServiceImpl extends ServiceImpl<CarFilmTenantMapper, CarFilmTenant> implements ICarFilmTenantService {

    @Autowired
    private CarFilmTenantMapper carFilmTenantMapper;

    /**
     * 查询不被软删除的数据
     *
     * @param
     * @return
     */
    @Override
    public List<CarFilmTenant> list(String productNo, String productName, String companyName, String companyNo, String managerName, String managerPhone, String miniProgramFlag, String officialWebsiteFlag) {
        QueryWrapper<CarFilmTenant> wrapper = new QueryWrapper<>();
        List<CarFilmTenant> carFilmTenantList = this.listByAuthorizedUsername();
        wrapper.in("product_no", carFilmTenantList.stream().map(CarFilmTenant::getProductNo).collect(Collectors.toList()));
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
        return carFilmTenantMapper.selectList(wrapper);
    }

    @Override
    public List<CarFilmTenant> listByAuthorizedUsername() {
        String username = OauthUtils.getUsername();
        QueryWrapper<CarFilmTenant> carFilmTenantQueryWrapper = new QueryWrapper<>();
        carFilmTenantQueryWrapper.like("authorized_username", "|" + username + "|");
        List<CarFilmTenant> carFilmTenantList = super.list(carFilmTenantQueryWrapper);
        log.debug("username：{}, carFilmTenantList:{}", username, carFilmTenantList);
        return carFilmTenantList;
    }

    /**
     * 分页查询所有不被软删除的数据 外加模糊查询
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public IPage<CarFilmTenant> pages(int current, int size, String productNo, String productName, String companyName, String companyNo, String managerName, String managerPhone, String miniProgramFlag, String officialWebsiteFlag) {
        QueryWrapper<CarFilmTenant> wrapper = new QueryWrapper<>();
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
        Page<CarFilmTenant> page = new Page<>(current, size);
        return carFilmTenantMapper.selectPage(page, wrapper);
    }

    /**
     * 按id查询 不可以查询到软删除的用户
     *
     * @param id id
     * @return
     */
    @Override
    public CarFilmTenant getById(Long id) {
        return carFilmTenantMapper.selectOne(new QueryWrapper<CarFilmTenant>().eq("delete_flag", 0).eq("id", id));
    }

    /**
     * 软删除
     *
     * @param carFilmProduct
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void remove(CarFilmTenant carFilmProduct) {
        carFilmProduct.setDeleteFlag(true);
        carFilmTenantMapper.updateById(carFilmProduct);
    }

    /**
     * 增加商品
     *
     * @param carFilmProduct
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(CarFilmTenant carFilmProduct) {
        return SqlHelper.retBool(carFilmTenantMapper.insert(carFilmProduct));
    }

    /**
     * 更改商品
     *
     * @param carFilmProduct
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(CarFilmTenant carFilmProduct) {
        carFilmTenantMapper.updateById(carFilmProduct);
    }
}
