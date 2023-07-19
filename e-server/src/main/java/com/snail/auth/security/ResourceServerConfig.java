package com.snail.auth.security;

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
                .antMatchers("/car-file-quality/save",
                        "/car-file-quality/update",
                        "/car-file-quality/delete",
                        "/car-file-quality/pages",
                        "/car-file-quality/getById",
                        "/car-file-quality/list",

                        "/car-file-product-level/save",
                        "/car-file-product-level/update",
                        "/car-file-product-level/delete",
                        "/car-file-product-level/pages",
                        "/car-file-product-level/getById",
                        "/car-file-product-level/list",

                        "/car-file-price/save",
                        "/car-file-price/update",
                        "/car-file-price/delete",
                        "/car-file-price/pages",
                        "/car-file-price/getById",
                        "/car-file-price/list",

                        "/car-file-message/save",
                        "/car-file-message/update",
                        "/car-file-message/delete",
                        "/car-file-message/pages",
                        "/car-file-message/getById",
                        "/car-file-message/list",

                        "/car-model/save",
                        "/car-model/update",
                        "/car-model/delete",
                        "/car-model/pages",
                        "/car-model/getById",
                        "/car-model/list",

                        "/car-brand/save",
                        "/car-brand/like",
                        "/car-brand/update",
                        "/car-brand/delete",
                        "/car-brand/pages",
                        "/car-brand/getById",
                        "/car-brand/list",
                        "/car-film-product/delete",
                        "/car-film-product/pages",
                        "/car-film-product/save",
                        "/car-film-product/update",
                        "/car-film-product/getById",
                        "/car-film-product/list",
                        "/zero-product/delete",
                        "/zero-product/update",
                        "/zero-product/save",
                        "/zero-product/get",
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
                        "/tank-brand/getAll",
                        "/tank-model/getAll",
                        "/tank-price/getAll",
                        "/tank-quality/getByCondition",
                        "/tank-demo/getLast",
                        "/tank-demo/getAll",
                        "/king-brand/getAll",
                        "/king-model/getAll",
                        "/king-price/getAll",
                        "/king-store/get",
                        "/king-store/getClose",
                        "/king-technician/get",
                        "/king-quality/get",
                        "/aili-brand/getAll",
                        "/aili-model/getAll",
                        "/aili-price/getAll",
                        "/aili-quality/getByCondition",
                        "/aili-message/baseSave",
                        "/aili-quality/baseSave",
                        "/command_record/get",
                        "/command_record/finish",
                        "/subscribe/**",
                        "/ssh/**",
                        "/traffic/save",
                        "/node/getConfiguration",
                        "/node/test",
                        "/server/refresh",
                        "/role/getPermissionIdList",
                        "/zero-auth/login")
                .permitAll()
                .anyRequest()
                .authenticated()
        ;
    }
}
