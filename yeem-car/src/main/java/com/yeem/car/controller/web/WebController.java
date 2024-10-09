package com.yeem.car.controller.web;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.*;

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
            Document brandDoc = Jsoup.connect("https://car.autohome.com.cn/AsLeftMenu/As_LeftListNew.ashx?typeId=1%20&brandId=0%20&fctId=0%20&seriesId=0")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36 Edg/129.0.0.0")
                    .get();
            Elements brandElements = brandDoc.getElementsByTag("li");
            int i = 0;
            for (Element brandElement : brandElements) {
                i++;
                double score = (double) i / (double) brandElements.size() * 100;
                System.out.println("处理进度:" + score + "%");
                String brandId = brandElement.attr("id").replace("b", "");
                String brandName = brandElement.text().substring(0, brandElement.text().indexOf("("));
                String brandNameEn = PinyinUtil.getPinyin(brandName);
                String brandUrl = brandElement.getElementsByTag("a").first().attr("href");
                System.out.println("汽车品牌ID：" + brandElement.attr("id").replace("b", ""));
                System.out.println("汽车品牌名称：" + brandElement.text().substring(0, brandElement.text().indexOf("(")));
                Thread.sleep(3500);
                // 在售
                Set<String> modelSet = new HashSet<>();
                String brandLogo = "";
                List<Document> modelZList = new ArrayList<>();
                int z = 1;
                while (true) {
                    if (z==1){
                        String url = "https://car.autohome.com.cn" + brandUrl;
                        Document modelZ = Jsoup.connect(url)
                                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36 Edg/129.0.0.0")
                                .get();
                        modelZList.add(modelZ);
                        brandLogo = "https:" + modelZ.getElementsByClass("carbradn-pic").first().getElementsByTag("img").first().attr("src");
                        if (!modelZ.location().equals(url)) {
                            break;
                        }
                    } else {
                        String url = "https://car.autohome.com.cn" + brandUrl.substring(0, brandUrl.indexOf(".")) + "-0-0-" + z +".html";
                        Document modelZ = Jsoup.connect(url)
                                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36 Edg/129.0.0.0")
                                .get();
                        Elements elementsZ = modelZ.getElementsByClass("list-cont");
                        if (elementsZ.size() == 0) {
                            break;
                        }
                        brandLogo = "https:" + modelZ.getElementsByClass("carbradn-pic").first().getElementsByTag("img").first().attr("src");
                        modelZList.add(modelZ);
                    }
                    Thread.sleep(3500);
                    z++;
                }
                for (Document modelZ : modelZList) {
                    Elements elementsZ = modelZ.getElementsByClass("list-cont");
                    brandLogo = "https:" + modelZ.getElementsByClass("carbradn-pic").first().getElementsByTag("img").first().attr("src");
                    if (elementsZ.size() == 0) {
                        break;
                    }
                    for (Element element : elementsZ) {
                        String modelName = element.getElementsByClass("list-cont-main").first().getElementsByTag("a").first().text();
                        String modelLevelName = element.getElementsByTag("li").first().getElementsByTag("span").first().text();
                        String modelLevelNo = getLevelNo(modelLevelName);
                        modelSet.add(modelName + "," + modelLevelNo);
                    }
                }
                // 停售
                int t = 1;
                List<Document> modelTList = new ArrayList<>();
                while (true) {
                    String url = "https://car.autohome.com.cn" + brandUrl.substring(0, brandUrl.indexOf(".")) + "-0-3-" + t + ".html";
                    Document modelT = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36 Edg/129.0.0.0")
                            .get();
                    Elements elementsT = modelT.getElementsByClass("list-cont");
                    if (elementsT.size() == 0) {
                        break;
                    }
                    modelTList.add(modelT);
                    for (Element element : elementsT) {
                        String modelName = element.getElementsByClass("list-cont-main").first().getElementsByTag("a").first().text();
                        String modelLevelName = element.getElementsByTag("li").first().getElementsByTag("span").first().text();
                        String modelLevelNo = getLevelNo(modelLevelName);
                        modelSet.add(modelName + "," + modelLevelNo);
                    }
                    Thread.sleep(3500);
                    t++;
                }
                for (Document modelT : modelTList) {
                    Elements elementsT = modelT.getElementsByClass("list-cont");
                    for (Element element : elementsT) {
                        String modelName = element.getElementsByClass("list-cont-main").first().getElementsByTag("a").first().text();
                        String modelLevelName = element.getElementsByTag("li").first().getElementsByTag("span").first().text();
                        String modelLevelNo = getLevelNo(modelLevelName);
                        modelSet.add(modelName + "," + modelLevelNo);
                    }
                }
                LambdaQueryWrapper<BaseCarBrand> baseCarBrandLambdaQueryWrapper = new LambdaQueryWrapper<>();
                baseCarBrandLambdaQueryWrapper.eq(BaseCarBrand::getName, brandName);
                int brandCount = brandService.selectCount(baseCarBrandLambdaQueryWrapper);
                if (brandCount == 0) {
                    BaseCarBrand baseCarBrand = new BaseCarBrand();
                    baseCarBrand.setName(brandName);
                    baseCarBrand.setNameEn(brandNameEn);
                    baseCarBrand.setLogoPath(brandLogo);
                    brandService.insert(baseCarBrand);
                }
                BaseCarBrand baseCarBrand = brandService.selectOne(baseCarBrandLambdaQueryWrapper);
                Long baseCarBrandId = baseCarBrand.getId();
                for (String model : modelSet) {
                    LambdaQueryWrapper<BaseCarModel> baseCarModelQueryWrapper = new LambdaQueryWrapper<>();
                    baseCarModelQueryWrapper.eq(BaseCarModel::getName, model.split(",")[0]);
                    int modelCount = carModelService.selectCount(baseCarModelQueryWrapper);
                    if (modelCount == 0) {
                        BaseCarModel baseCarModel = new BaseCarModel();
                        baseCarModel.setName(model.split(",")[0]);
                        baseCarModel.setNameEn(PinyinUtil.getPinyin(model.split(",")[0]));
                        baseCarModel.setBrandId(baseCarBrandId);
                        baseCarModel.setLevelNo(model.split(",")[1]);
                        carModelService.insert(baseCarModel);
                        log.info("插入车型：{}", model);
                    } else {
                        log.info("已存在车型：{}", model);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    private static String getLevelNo(String levelName) {
        if (levelName.equals("微型车")) {
            return "11";
        } else if (levelName.equals("小型车")) {
            return "12";
        } else if (levelName.equals("紧凑型车")) {
            return "13";
        } else if (levelName.equals("中型车")) {
            return "14";
        } else if (levelName.equals("中大型车")) {
            return "15";
        } else if (levelName.equals("大型车")) {
            return "16";
        } else if (levelName.equals("小型SUV")) {
            return "21";
        } else if (levelName.equals("紧凑型SUV")) {
            return "22";
        } else if (levelName.equals("中型SUV")) {
            return "23";
        } else if (levelName.equals("中大型SUV")) {
            return "24";
        } else if (levelName.equals("大型SUV")) {
            return "25";
        } else if (levelName.equals("紧凑型MPV")) {
            return "31";
        } else if (levelName.equals("中型MPV")) {
            return "32";
        } else if (levelName.equals("中大型MPV")) {
            return "33";
        } else if (levelName.equals("大型MPV")) {
            return "34";
        } else if (levelName.equals("跑车")) {
            return "40";
        } else if (levelName.equals("皮卡")) {
            return "50";
        } else if (levelName.equals("微面")) {
            return "91";
        } else if (levelName.equals("微卡")) {
            return "92";
        } else if (levelName.equals("轻客")) {
            return "93";
        } else {
            return "0";
        }
    }
}
