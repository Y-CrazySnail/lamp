package com.yeem.zero.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WechatInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WechatAuthInterceptor())
                .addPathPatterns("/wechat/**")
                .excludePathPatterns("/wechat/zero-user/login")
                .excludePathPatterns("/wechat/zero-order/paymentCallback");
    }
}