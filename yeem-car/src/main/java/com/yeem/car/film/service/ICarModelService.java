package com.yeem.car.film.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car.film.entity.BaseCarModel;

import java.util.List;

public interface ICarModelService extends IService<BaseCarModel> {
    /**
     * id查询所有
     *
     * @return
     */
    List<BaseCarModel> list();

    /**
     * Id查询所有不被软删除的数据
     *
     * @return
     */
    List<BaseCarModel> listByBrandId(Long id);

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @return
     */
    IPage<BaseCarModel> pages(int current, int size, String name);


    /**
     * id单独查询
     *
     * @param id
     * @return
     */
    BaseCarModel getById(Long id);

    /**
     * 删除
     *
     * @param id
     */
    void removeByBrandId(Long id);

    void remove(Long id);

    /**
     * 增加
     *
     * @param baseCarModelList
     */
    boolean save(List<BaseCarModel> baseCarModelList, Long brandId);

    void insert(BaseCarModel baseCarModel);

    /**
     * 修改
     *
     * @param baseCarModelList
     */
    void update(List<BaseCarModel> baseCarModelList);

}
