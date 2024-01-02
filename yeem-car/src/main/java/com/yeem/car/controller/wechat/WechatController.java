package com.yeem.car.controller.wechat;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.car.entity.BaseCarBrand;
import com.yeem.car.entity.BaseCarLevel;
import com.yeem.car.entity.CarFilmProduct;
import com.yeem.car.entity.CarFilmQuality;
import com.yeem.car.service.ICarBrandService;
import com.yeem.car.service.ICarFilmProductService;
import com.yeem.car.service.ICarFilmQualityService;
import com.yeem.car.service.ICarLevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
//    @Value("${test.name}")
//    private String test;


    /**
     * 查询小程序基础信息
     * @return
     */
//    @GetMapping("/ok")
//    public ResponseEntity<Object> ok() {
//        Map<String, Object> result = new HashMap<>();
//        try {
//            System.out.println(test);
//            return null;
//        } catch (Exception e) {
//            log.error("查询失败", e);
//            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询失败");
//        }
//    }

    /**
     * 查询小程序基础信息
     *
     * @param productNo
     * @return
     */
    @GetMapping("/getBaseInfo")
    public ResponseEntity<Object> getBaseInfo(@RequestParam(value = "productNo") String productNo) {
        Map<String, Object> result = new HashMap<>();
        try {
            QueryWrapper<BaseCarBrand> baseCarBrandQueryWrapper = new QueryWrapper<>();
            List<BaseCarBrand> baseCarBrandList = carBrandService.list();
            result.put("brandList", baseCarBrandList);
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
     *
     * @param productNo 产品代码
     * @param queryKey 查询关键字
     * @return 质保信息列表
     */
    @GetMapping("getQualityInfo")
    public ResponseEntity<Object> getQualityInfo(@RequestParam(value = "productNo") String productNo,
                                                 @RequestParam(value = "queryKey") String queryKey) {
        if (StringUtils.isEmpty(queryKey)) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询失败");
        }
        try {
            List<CarFilmQuality> carFilmQualityList = carFilmQualityService.getQualityInfo(productNo, queryKey);
            return ResponseEntity.ok(carFilmQualityList);
        } catch (Exception e) {
            log.error("查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询失败");
        }
    }
}
