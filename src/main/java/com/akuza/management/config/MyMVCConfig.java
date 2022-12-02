package com.akuza.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// 自己的mvc配置
public class MyMVCConfig implements WebMvcConfigurer {
    @Override // 配置路由
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/main.html").setViewName("dashboard");
    }
    // 注册解析器
    @Bean
    LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

    // 拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns("/index.html","/","/login","/css/**","/js/**","/img/**"); // 对这些请求放行
    }
}

