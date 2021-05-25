package com.wushi.lade.web.result.configurer;

import com.wushi.lade.web.interceptor.LadeHandlerInterceptor;
import com.wushi.lade.web.result.handler.ActionResultReturnValueHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 注册自定义返回值处理器
 *
 * @author wushi
 * @date 2020/3/19 10:08
 * @description
 */
@Configuration
public class ActionResultWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${spring.profiles.active:dev}")
    private String active;

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new ActionResultReturnValueHandler(this.active));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LadeHandlerInterceptor()).addPathPatterns("/error/**");
    }

    /**
     * 解决  No mapping for GET /favicon.ico 访问静态资源图标
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/META-INF/resources/");
    }

}
