package com.yeem.lamp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final String SECURITY_OAUTH = "security-oauth.yml";

    @Override
    public void configure(HttpSecurity http) throws Exception {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource(SECURITY_OAUTH));
        Properties properties = yamlPropertiesFactoryBean.getObject();
        List<String> permitUrl = new ArrayList<>();
        if (null != properties) {
            properties.forEach((k, v) -> {
                log.info("permit url {}", v);
                permitUrl.add(String.valueOf(v));
            });
        }
        String[] permitUrlArray = permitUrl.toArray(new String[0]);
        http.authorizeRequests()
                .antMatchers(permitUrlArray)
                .permitAll()
                .anyRequest()
                .authenticated()
        ;
    }
}
