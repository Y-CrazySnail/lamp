package com.yeem.car_film_saas.Utis;


import com.yeem.car_film_saas.entity.SysTemplate;
import com.yeem.common.utils.OauthUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateTool {

    public SysTemplate getTemplate(SysTemplate sysTemplate ,Map<String, Object> root) {
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDefaultEncoding("utf-8");
        configuration.setCacheStorage(new freemarker.cache.NullCacheStorage());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", sysTemplate.getContent());
        configuration.setTemplateLoader(stringTemplateLoader);
        try {
            Template template = configuration.getTemplate("template");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
            sysTemplate.setSubject(null);
            sysTemplate.setContent(content);
            System.out.println(content);
            System.out.println(content);
            return sysTemplate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }

    }
}
