package com.lamp.common.utils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FreeMakerUtils {

    /**
     * 获取FreeMaker替换文本
     *
     * @param templatePath 模版路径
     * @param templateName 模版名称
     * @param replaceMap   替换Map
     * @return FreeMaker替换文本
     */
    public static String getContent(String templatePath, String templateName, Map<String, Object> replaceMap) {
        // 创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        Template template = null;
        String content = null;
        try {
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
            // 设置字符集
            configuration.setDefaultEncoding("utf-8");
            template = configuration.getTemplate(templateName);
            content = FreeMarkerTemplateUtils.processTemplateIntoString(template, replaceMap);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 获取FreeMaker替换文本
     *
     * @param templateContent 模版内容
     * @param replaceMap      替换Map
     * @return FreeMaker替换文本
     */
    public static String getContent(String templateContent, Map<String, Object> replaceMap) {
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDefaultEncoding("utf-8");
        configuration.setCacheStorage(new freemarker.cache.NullCacheStorage());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateContent);
        configuration.setTemplateLoader(stringTemplateLoader);
        try {
            Template template = configuration.getTemplate("template");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, replaceMap);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String template = "你好：${param1},我是：${param2}";
        Map<String, Object> map = new HashMap<>();
        map.put("param1", "张三");
        map.put("param2", "李四");
        System.out.println(getContent(template, map));
    }
}
