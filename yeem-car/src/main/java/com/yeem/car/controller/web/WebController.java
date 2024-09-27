package com.yeem.car.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.car.entity.BaseCarBrand;
import com.yeem.car.entity.BaseCarModel;
import com.yeem.car.mapper.CarBrandMapper;
import com.yeem.car.mapper.CarModelMapper;
import com.yeem.car.service.ICarBrandService;
import com.yeem.car.service.ICarModelService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/web")
public class WebController {

    @Autowired
    private CarModelMapper carModelService;

    @Autowired
    private CarBrandMapper brandService;

    @RequestMapping("test")
    public String spider() {
        try {
            String filePath = "/Users/snail/Desktop/car.sql";
            File file = new File(filePath);
            Document brandDoc = Jsoup.connect("https://m.dongchedi.com/auto/library/x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x").get();
            Elements brandElements = brandDoc.getElementsByTag("script");
            int i = 0;
            for (Element brandElement : brandElements) {
                if (Objects.equals(brandElement.attr("id"), "__NEXT_DATA__")) {
                    String brandData = brandElement.html();
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode node = mapper.readTree(brandData).get("props").get("pageProps").get("allBrands").get("brand");
                    for (JsonNode brand : node) {
                        if (Objects.equals(String.valueOf(brand.get("type")), "1000")) {
                            log.info("1000类型:{}", brand.get("info"));
                        } else {
                            Thread.sleep(1020);
                            Document modelDoc = Jsoup.connect("https://m.dongchedi.com/auto/library-brand/" + brand.get("info").get("brand_id"))
                                    .referrer("https://m.dongchedi.com/auto/library/x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x")
                                    .userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1 Edg/127.0.0.0")
                                    .get();
                            Elements modelElements = modelDoc.getElementsByTag("script");
                            for (Element modelElement : modelElements) {
                                if (Objects.equals(modelElement.attr("id"), "__NEXT_DATA__")) {
                                    String modelData = modelElement.html();
                                    JsonNode modelNodes = mapper.readTree(modelData).get("props").get("pageProps").get("brandData").get("category_list");
                                    for (JsonNode modelNode : modelNodes) {
                                        if (!String.valueOf(modelNode.get("category_code")).contains("all")) {
                                            continue;
                                        }
                                        if (null == modelNode.get("list")) {
                                            continue;
                                        }
                                        boolean valid = false;
                                        for (JsonNode model : modelNode.get("list")) {
                                            if (Objects.equals(String.valueOf(model.get("type")), "1075")) {
                                                valid = !String.valueOf(model.get("info").get("text")).contains("未上市");
                                                continue;
                                            }
                                            if (valid) {
//                                                System.out.println(model.get("info"));
                                            }
                                            Thread.sleep(1020);
                                            Document detailDoc = Jsoup.connect("https://m.dongchedi.com/auto/series/" + model.get("info").get("series_id")).get();
                                            Elements detailElements = detailDoc.getElementsByTag("script");
                                            for (Element detailElement : detailElements) {
                                                if (Objects.equals(detailElement.attr("id"), "__NEXT_DATA__")) {
                                                    String detailData = detailElement.html();
                                                    JsonNode detailNodes = mapper.readTree(detailData).get("props").get("pageProps").get("seriesHomeHead");
                                                    System.out.println(brand.get("info").get("brand_name").toString().replace("\"", ""));
                                                    System.out.println(detailNodes.get("car_type").toString().replace("\"", ""));
                                                    System.out.println(detailNodes.get("series_type").toString().replace("\"", ""));
                                                    System.out.println(detailNodes.get("series_name").toString().replace("\"", ""));
                                                    String brandName = brand.get("info").get("brand_name").toString().replace("\"", "");
                                                    String carType = detailNodes.get("car_type").toString().replace("\"", "");
                                                    String seriesType = detailNodes.get("series_type").toString().replace("\"", "");
                                                    String seriesName = detailNodes.get("series_name").toString().replace("\"", "");

                                                    LambdaQueryWrapper<BaseCarBrand> queryWrapperBrand = new LambdaQueryWrapper<>();
                                                    queryWrapperBrand.eq(BaseCarBrand::getName, brandName);
                                                    BaseCarBrand baseCarBrand = brandService.selectOne(queryWrapperBrand);
                                                    if (Objects.isNull(baseCarBrand)) {
                                                        BaseCarBrand addBrand = new BaseCarBrand();
                                                        addBrand.setName(brandName);
                                                        brandService.insert(addBrand);
                                                    }
                                                    baseCarBrand = brandService.selectOne(queryWrapperBrand);
                                                    LambdaQueryWrapper<BaseCarModel> queryWrapper = new LambdaQueryWrapper<>();
                                                    queryWrapper.eq(BaseCarModel::getName, seriesName);
                                                    int count = carModelService.selectCount(queryWrapper);
                                                    if (count == 0) {
                                                        BaseCarModel baseCarModel = new BaseCarModel();
                                                        baseCarModel.setBrandId(baseCarBrand.getId());
                                                        baseCarModel.setName(seriesName);
                                                        baseCarModel.setCreateUser(carType+"_"+seriesType);
                                                        carModelService.insert(baseCarModel);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
