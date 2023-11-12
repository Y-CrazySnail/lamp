package com.yeem.car_film_saas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car_film_saas.entity.CarFilmTenant;

import java.util.List;


public interface ICarFilmTenantService extends IService<CarFilmTenant> {
    /**
     * 查询所有不被软删除的数据
     *
     * @return
     */
    List<CarFilmTenant> list(String productNo, String productName, String companyName, String  companyNo, String  managerName, String  managerPhone , String  miniProgramFlag, String officialWebsiteFlag);

    List<CarFilmTenant> listByAuthorizedUsername();

    /**
     * 分页查询
     *
     * @return
     */
    IPage<CarFilmTenant> pages(int current, int size, String productNo, String productName, String companyName, String  companyNo, String  managerName, String  managerPhone , String  miniProgramFlag, String officialWebsiteFlag);

    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    CarFilmTenant getById(Long id);

    /**
     * 软删除商品
     *
     * @param
     */
    void remove(CarFilmTenant carFilmProduct);

    /**
     * 新增商品
     *
     * @param carFilmProduct
     */
    boolean save(CarFilmTenant carFilmProduct);

    /**
     * 更改商品
     *
     * @param carFilmProduct
     */
    void update(CarFilmTenant carFilmProduct);
}
