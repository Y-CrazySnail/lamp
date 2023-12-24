package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

import java.util.List;


@TableName(value = "base_car_brand")
public class BaseCarBrand extends BaseEntity {

    private String name;

    private String nameEn;

    private String logoPath;

    @TableField(exist = false)
    private List<BaseCarModel> baseCarModelList;

    @TableField(exist = false)
    private Integer carModelCount;

    public BaseCarBrand() {
    }

    public BaseCarBrand(String name, String nameEn, String logoPath, String logoName, List<BaseCarModel> baseCarModelList) {
        this.name = name;
        this.nameEn = nameEn;
        this.logoPath = logoPath;
        this.baseCarModelList = baseCarModelList;
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

    public List<BaseCarModel> getCarModelList() {
        return baseCarModelList;
    }

    public void setCarModelList(List<BaseCarModel> baseCarModelList) {
        this.baseCarModelList = baseCarModelList;
    }
}
