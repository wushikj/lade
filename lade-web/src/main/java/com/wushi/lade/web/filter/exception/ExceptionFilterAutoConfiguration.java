package com.wushi.lade.web.filter.exception;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤异常处理注册
 *
 * @author wushi
 * @date 2020/2/5 14:24
 */
@Configuration
public class ExceptionFilterAutoConfiguration {

    @Bean
    public FilterRegistrationBean<ExceptionFilter> exceptionFilterRegistrationBean() {
        FilterRegistrationBean<ExceptionFilter> registration = new FilterRegistrationBean<>();
        ExceptionFilter exceptionFilter = new ExceptionFilter();
        registration.setFilter(exceptionFilter);
        registration.addUrlPatterns("/*");
        registration.setName("exceptionFilter");
        registration.setOrder(1);
        return registration;
    }
    
}
