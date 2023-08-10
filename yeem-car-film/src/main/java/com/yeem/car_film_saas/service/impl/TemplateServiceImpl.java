package com.yeem.car_film_saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.car_film_saas.Utis.TemplateTool;

import com.yeem.car_film_saas.mapper.TemplateMapper;
import com.yeem.car_film_saas.entity.SysTemplate;
import com.yeem.car_film_saas.service.ITemplateService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Service
public class TemplateServiceImpl implements ITemplateService {
    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public SysTemplate getFinishedEmailTemplate(SysTemplate sysTemplate, Map<String, Object> root) {
        // =================================================
        // 数据库中查到 name type 合格的一条模板
        SysTemplate DBTemplate = templateMapper.selectOne(new QueryWrapper<SysTemplate>().eq("name", sysTemplate.getName()).eq("type", sysTemplate.getType()));
        // ==================================================
        // 加工完成的模板
        SysTemplate finishedTemplate = this.getTemplate(DBTemplate,root);
        return finishedTemplate;
    }
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
            sysTemplate.setContent(content);
            System.out.println(content);
            return sysTemplate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}

