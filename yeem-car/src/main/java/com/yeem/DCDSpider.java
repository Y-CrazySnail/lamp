package com.yeem;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.node.ArrayNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class DCDSpider {
    public static final String INSERT_BRAND_SQL = "INSERT INTO base_car_brand_new (id, name, name_en, logo_path) VALUES(${id}, '${name}', '${name_en}', '${logo_path}');";
    public static final String INSERT_MODEL_SQL = "INSERT INTO base_car_model_new (name, name_en, brand_id, level_no) VALUES('${name}', '${name_en}', ${brand_id}, '${level_no}');";

    public static void main(String[] args) {
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
