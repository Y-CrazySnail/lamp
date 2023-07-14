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
                .antMatchers(
                        "/carmodel/save",
                        "/carmodel/update",
                        "/carmodel/delete",
                        "/carmodel/pagegetall",
                        "/carmodel/getid",
                        "/carmodel/getall",

                        "/carbrand/save",
                        "/carbrand/update",
                        "/carbrand/delete",
                        "/carbrand/pagegetall",
                        "/carbrand/getid",
                        "/carbrand/getall",
                        "/carfilmproduct/delete",
                        "/carfilmproduct/pageget",
                        "/carfilmproduct/save",
                        "/carfilmproduct/update",
                        "/carfilmproduct/getid",
                        "/carfilmproduct/getall",
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
