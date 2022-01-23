package com.snail.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/chinaybop-brand/getAll",
                        "/chinaybop-model/getAll",
                        "/chinaybop-price/getAll",
                        "/chinaybop-quality/getByCondition",
                        "/chinaybop-news/getAll",
                        "/chinaybop-news/getById",
                        "/chinaybop-message/baseSave",
                        "/xpxl-brand/getAll",
                        "/xpxl-model/getAll",
                        "/xpxl-price/getAll",
                        "/xpxl-quality/getByCondition",
                        "/xpxl-demo/getLast",
                        "/xpxl-demo/getAll",
                        "/aili-quality/getByCondition")
                .permitAll()
                .antMatchers("/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html/**",
                        "/webjars/**")
                .permitAll()
                .anyRequest()
                .authenticated()
        ;
    }
}
