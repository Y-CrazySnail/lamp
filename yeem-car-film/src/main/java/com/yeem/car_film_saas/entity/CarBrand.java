package com.yeem.car_film_saas.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

import java.util.List;


@TableName(value = "base_car_brand")
public class CarBrand extends BaseEntity {

    private String name;

    private String nameEn;

    private String logoPath;

    private  String logoName;
    @TableField(exist = false)
    private List<CarModel> carModelList;

    @TableField(exist = false)
    private Integer carModelCount;

    public CarBrand() {
    }

    public CarBrand(String name, String nameEn, String logoPath, String logoName, List<CarModel> carModelList) {
        this.name = name;
        this.nameEn = nameEn;
        this.logoPath = logoPath;
        this.logoName = logoName;
        this.carModelList = carModelList;
    }

    public Integer getCarModelCount() {
        return carModelCount;
    }

    public void setCarModelCount(Integer carModelCount) {
        this.carModelCount = carModelCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public List<CarModel> getCarModelList() {
        return carModelList;
    }

    public void setCarModelList(List<CarModel> carModelList) {
        this.carModelList = carModelList;
    }
}
