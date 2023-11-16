package com.yeem.car.film.controller.wechat;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.car.film.entity.BaseCarBrand;
import com.yeem.car.film.entity.BaseCarLevel;
import com.yeem.car.film.entity.CarFilmProduct;
import com.yeem.car.film.entity.CarFilmQuality;
import com.yeem.car.film.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    private ICarBrandService carBrandService;
    @Autowired
    private ICarLevelService carLevelService;
    @Autowired
    private ICarFilmProductService carFilmProductService;
    @Autowired
    private ICarFilmQualityService carFilmQualityService;

    /**
     * 查询小程序基础信息
     * @param httpServletRequest
     * @param productNo
     * @return
     */
    @GetMapping("/getBaseInfo")
    public ResponseEntity<Object> getBaseInfo(HttpServletRequest httpServletRequest,
                                          @RequestParam(value = "productNo", required = false) String productNo) {
        Map<String, Object> result = new HashMap<>();
        try {
            QueryWrapper<BaseCarBrand> baseCarBrandQueryWrapper = new QueryWrapper<>();
            List<BaseCarBrand> baseCarBrandList = carBrandService.list();
            result.put("brandList", baseCarBrandList);
            List<BaseCarLevel> baseCarLevelList = carLevelService.list();
            result.put("levelList", baseCarLevelList);
            List<CarFilmProduct> carFilmProductList = carFilmProductService.listWithPrice(productNo);
            result.put("productList", carFilmProductList);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询失败");
        }
    }

    /**
     * 查询质保信息
     * @param httpServletRequest
     * @param productNo
     * @return
     */
    @GetMapping("getQualityInfo")
    public ResponseEntity<Object> getQualityInfo(HttpServletRequest httpServletRequest,
                                          @RequestParam(value = "productNo", required = false) String productNo) {
        try {
            List<CarFilmQuality> carFilmQualityList = carFilmQualityService.getQualityInfo(productNo, "");
            return ResponseEntity.ok(carFilmQualityList);
        } catch (Exception e) {
            log.error("查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询失败");
        }
    }
}
