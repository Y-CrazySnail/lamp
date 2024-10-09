package com.yeem;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class JsoupTest {
    public static final String INSERT_BRAND_SQL = "INSERT INTO base_car_brand_new (id, name, name_en, logo_path) VALUES(${id}, '${name}', '${name_en}', '${logo_path}');";
    public static final String INSERT_MODEL_SQL = "INSERT INTO base_car_model_new (name, name_en, brand_id, level_no) VALUES('${name}', '${name_en}', ${brand_id}, '${level_no}');";

    public static void main(String[] args) {
        try {
            String filePath = "/Users/snail/Desktop/car.sql";
            File file = new File(filePath);
            Document brandDoc = Jsoup.connect("https://car.autohome.com.cn/AsLeftMenu/As_LeftListNew.ashx?typeId=1%20&brandId=0%20&fctId=0%20&seriesId=0").get();
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
                FileUtil.writeUtf8String("-- 汽车品牌名称：" + brandElement.text().substring(0, brandElement.text().indexOf("(")), file);
                Thread.sleep(3500);
                // 在售
                Set<String> modelSet = new HashSet<>();
                String brandLogo = "";
                for (int j=1; j<100; j++){
                    Document modelZ;
                    if (j==1){
                        modelZ = Jsoup.connect("https://car.autohome.com.cn" + brandUrl).get();
                    } else {
                        modelZ = Jsoup.connect("https://car.autohome.com.cn" + brandUrl.substring(0, brandUrl.indexOf(".")) + "-0-0-" + j +".html").get();
                    }
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
                    Thread.sleep(3500);
                }
                // 停售
                for (int j=1; j<100; j++) {
                    Document modelT = Jsoup.connect("https://car.autohome.com.cn" + brandUrl.substring(0, brandUrl.indexOf(".")) + "-0-3-" + j + ".html").get();
                    Elements elementsT = modelT.getElementsByClass("list-cont");
                    if (elementsT.size() == 0) {
                        break;
                    }
                    for (Element element : elementsT) {
                        String modelName = element.getElementsByClass("list-cont-main").first().getElementsByTag("a").first().text();
                        String modelLevelName = element.getElementsByTag("li").first().getElementsByTag("span").first().text();
                        String modelLevelNo = getLevelNo(modelLevelName);
                        modelSet.add(modelName + "," + modelLevelNo);
                    }
                }
                String brandSQL = INSERT_BRAND_SQL.replace("${id}", brandId)
                        .replace("${name}", brandName)
                        .replace("${name_en}", brandNameEn)
                        .replace("${logo_path}", brandLogo);
                System.out.println(brandSQL);
                FileUtil.writeUtf8String(brandSQL, file);
                for (String model : modelSet) {
                    String modelSQL = INSERT_MODEL_SQL.replace("${name}", model.split(",")[0])
                            .replace("${name_en}", PinyinUtil.getPinyin(model.split(",")[0]))
                            .replace("${brand_id}", brandId)
                            .replace("${level_no}", model.split(",")[1]);
                    System.out.println(modelSQL);
                    FileUtil.writeUtf8String(modelSQL, file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
