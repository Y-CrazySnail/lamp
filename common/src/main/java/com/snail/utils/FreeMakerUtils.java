package com.snail.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FreeMakerUtils {

    /**
     * 获取FreeMaker配置
     *
     * @param templatePath 模版路径
     * @return FreeMaker配置
     */
    public static Configuration getConfiguration(String templatePath) {
        // 创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        try {
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 设置字符集
        configuration.setDefaultEncoding("utf-8");
        return configuration;
    }

    /**
     * 获取FreeMaker模版
     *
     * @param templatePath 模版路径
     * @param templateName 模版名称
     * @return FreeMaker模版
     */
    public static Template getTemplate(String templatePath, String templateName) {
        Configuration configuration = getConfiguration(templatePath);
        Template template = null;
        try {
            template = configuration.getTemplate(templateName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }

    /**
     * 获取FreeMaker替换文本
     *
     * @param templatePath 模版路径
     * @param templateName 模版名称
     * @param replaceMap   替换Map
     * @return FreeMaker替换文本
     */
    public static String getContent(String templatePath, String templateName, Map<String, Object> replaceMap) {
        Template template = getTemplate(templatePath, templateName);
        String content = null;
        try {
            content = FreeMarkerTemplateUtils.processTemplateIntoString(template, replaceMap);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return content;
    }
}
