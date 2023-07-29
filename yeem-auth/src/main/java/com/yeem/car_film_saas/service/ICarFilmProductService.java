package com.yeem.car_film_saas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car_film_saas.entity.CarFilmProduct;

import java.util.List;


public interface ICarFilmProductService extends IService<CarFilmProduct> {
    /**
     * 查询所有不被软删除的数据
     *
     * @return
     */
    List<CarFilmProduct> list(String productNo, String productName, String companyName, String  companyNo,String  managerName,String  managerPhone , String  miniProgramFlag,String officialWebsiteFlag);

    /**
     * 分页查询
     *
     * @return
     */
    IPage<CarFilmProduct> pages(int current, int size, String productNo, String productName, String companyName, String  companyNo,String  managerName,String  managerPhone , String  miniProgramFlag,String officialWebsiteFlag);

    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    CarFilmProduct getById(Long id);

    /**
     * 软删除商品
     *
     * @param
     */
    void remove(CarFilmProduct carFilmProduct);

    /**
     * 新增商品
     *
     * @param carFilmProduct
     */
    boolean save(CarFilmProduct carFilmProduct);

    /**
     * 更改商品
     *
     * @param carFilmProduct
     */
    void update(CarFilmProduct carFilmProduct);
}
