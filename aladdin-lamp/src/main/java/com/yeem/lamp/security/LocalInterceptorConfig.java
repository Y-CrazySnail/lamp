package com.yeem.lamp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class LocalInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocalAuthInterceptor())
                .addPathPatterns("/web/**")
                .excludePathPatterns("/web/member/login")
                .excludePathPatterns("/web/order/pay");
    }
}