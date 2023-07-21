package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroProduct;

import java.util.List;

public interface IZeroProductService extends IService<ZeroProduct> {
    List<ZeroProduct> listByCategoryId(Long categoryId);

    /**
     * 根据id查询的接口
     * @param id 参数
     * @return ZeroProduct 产品信息
     */
    ZeroProduct getById(Long id);

    /**
     * 增加一个酒类
     * @param zeroProduct 产品信息
     */
    void addProduct(ZeroProduct zeroProduct);

    /**
     * 更新商品
     * @param zeroProduct 产品信息
     */
    void updateProduct(ZeroProduct zeroProduct);

    /**
     * 软删除商品
     * @param zeroProduct 产品信息id
     */
    void removeProduct(ZeroProduct zeroProduct);
}
