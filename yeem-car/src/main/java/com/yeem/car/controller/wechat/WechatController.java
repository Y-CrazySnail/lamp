package com.yeem.car.controller.wechat;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.car.config.Constant;
import com.yeem.car.entity.*;
import com.yeem.car.security.WechatAuthInterceptor;
import com.yeem.car.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private ICarFilmUserService carFilmUserService;

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
     * @param queryKey  查询关键字
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

    @PostMapping("saveQualityInfo")
    public ResponseEntity<Object> saveQualityInfo(@RequestBody CarFilmQuality carFilmQuality) {
        Long userId = WechatAuthInterceptor.getUserId();
        if (StringUtils.isEmpty(userId)) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("鉴权失败");
        }
        CarFilmUser carFilmUser = carFilmUserService.getById(userId);
        if (!Constant.TRUE_STRING.equals(carFilmUser.getQualityPermission())) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("鉴权失败");
        }
        try {
            carFilmQualityService.save(carFilmQuality);
            return ResponseEntity.ok("录入质保成功");
        } catch (Exception e) {
            log.error("input quality info error", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("录入质保失败");
        }
    }

    /**
     * 小程序登录
     *
     * @return 登录信息
     * @apiNote 小程序登录
     */
    @PostMapping("login")
    public ResponseEntity<CarFilmUser> login(@RequestBody CarFilmUser carFilmUser) {
        try {
            return ResponseEntity.ok(carFilmUserService.login(carFilmUser));
        } catch (Exception e) {
            log.error("wx login api error：", e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
