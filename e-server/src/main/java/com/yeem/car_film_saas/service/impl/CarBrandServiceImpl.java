package com.yeem.car_film_saas.service.impl;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.car_film_saas.entity.CarBrand;
import com.yeem.car_film_saas.entity.CarModel;
import com.yeem.car_film_saas.mapper.CarBrandMapper;
import com.yeem.car_film_saas.service.ICarBrandService;
import com.yeem.car_film_saas.service.ICarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CarBrandServiceImpl extends ServiceImpl<CarBrandMapper, CarBrand> implements ICarBrandService {
    @Autowired
    private CarBrandMapper carBrandMapper;
    @Autowired
    private ICarModelService carModelService;


    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public List<CarBrand> list() {
        // 拿到Brand表中没被软删除的
        List<CarBrand> carBrandList = carBrandMapper.selectList(new QueryWrapper<CarBrand>().eq("delete_flag", 0));
        // 便利出来
        for (CarBrand carBrand : carBrandList) {
            // 找到对应的数据 放进carBrand中
            carBrand.setCarModelList(carModelService.listByBrandId(carBrand.getId()));
        }
        return carBrandList;
    }

    /**
     * 分页模糊查询
     *
     * @param current
     * @param size
     * @param brandName
     * @return
     */
    @Override
    public IPage<CarBrand> page(int current, int size, String brandName) {
        QueryWrapper<CarBrand> carBrandQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(brandName)) {
            carBrandQueryWrapper.like("name", brandName);
        }
        IPage<CarBrand> page = new Page<>(current, size);
        return carBrandMapper.selectPage(page, carBrandQueryWrapper);
    }

    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    @Override
    public CarBrand getById(Long id) {
        QueryWrapper<CarBrand> carBrandQueryWrapper = new QueryWrapper<>();
        carBrandQueryWrapper.eq("delete_flag", 0).eq("id", id);
        CarBrand carBrand = carBrandMapper.selectOne(carBrandQueryWrapper);
        if (StringUtils.isEmpty(carBrand)) {
            return null;
        }
        List<CarModel> carModelist = carModelService.listByBrandId(id);
        carBrand.setCarModelList(carModelist);
        return carBrand;
    }

    /**
     * 软删除
     *
     * @param carBrand
     */
    @Override
    public void remove(CarBrand carBrand) {
        carModelService.removeByBrandId(carBrand.getId());
        carBrand.setDeleteFlag(true);
        carBrandMapper.updateById(carBrand);
    }

    /**
     * 更改
     *
     * @param carBrand
     */
    @Override
    public void update(CarBrand carBrand) {
        // 设置车牌的名称拼音和文件截取文件
        carBrand.setNameEn(PinyinUtil.getPinyin(carBrand.getName()));
        carBrand.setLogoName(FileNameUtil.getName(carBrand.getLogoPath()));
        // 更新汽车品牌
        carBrandMapper.updateById(carBrand);
        // 此车牌的全部车型
        List<CarModel> carBrandById_The_ModelList = this.getById(carBrand.getId()).getCarModelList();
        // 前端传进来的车型数据
        List<CarModel> carModelList = carBrand.getCarModelList();
        // 前端传进来的数据里的model没id 为新增项目
        for (CarModel model : carModelList) {
            // 有就更新一下
            if (!StringUtils.isEmpty(model.getId())) {
                // 更新车型
                model.setBrandId(carBrand.getId());
                System.out.println("走到了update");
//                carModelService.updateBatchById(carBrandById_The_ModelList);
                carModelService.updateById(model);
            }
            // 没有就新增
            if (StringUtils.isEmpty(model.getId())) {
                // 新增车型
//                carModelService.save(carModelList, carBrand.getId());
                model.setBrandId(carBrand.getId());
                carModelService.insert(model);
                System.out.println("走到了save");

            }
        }
        List<CarModel> xuhuanhoucarModelList = this.getById(carBrand.getId()).getCarModelList();
        // 遍历此车牌的全部车型
        for (CarModel DbModel : carBrandById_The_ModelList) {
            boolean flag = false;
            // 遍历前端传来的
            for (CarModel VueModel : xuhuanhoucarModelList) {
                // 如果前端传来的跟数据库中的对应不上
                if (!(DbModel.getId().equals(VueModel.getId()))) {
                    System.out.println("比较了");
                    flag = true;
                }
            }
            if (flag) {
                System.out.println("删除了");
                carModelService.remove(DbModel.getId());
            }
        }
    }

    /**
     * 新增
     *
     * @param carBrand
     * @return
     */
    @Override
    public boolean save(CarBrand carBrand) {
        carBrand.setNameEn(PinyinUtil.getPinyin(carBrand.getName()));
        carBrand.setLogoName(FileNameUtil.getName(carBrand.getLogoPath()));
        carBrandMapper.insert(carBrand);
        return carModelService.save(carBrand.getCarModelList(), carBrand.getId());
    }
}
