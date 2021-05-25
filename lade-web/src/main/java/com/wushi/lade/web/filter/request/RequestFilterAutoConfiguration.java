package com.wushi.lade.web.filter.request;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Request过滤器自动注册
 *
 * @author wushi
 * @date 2020/1/18 10:46
 */
@Configuration
public class RequestFilterAutoConfiguration {

    @Bean
    public FilterRegistrationBean<RequestFilter> requestFilterRegistrationBean() {
        FilterRegistrationBean<RequestFilter> registration = new FilterRegistrationBean<>();
        RequestFilter requestFilter = new RequestFilter();
        registration.setFilter(requestFilter);
        registration.addUrlPatterns("/*");
        registration.setName("requestFilter");
        registration.setOrder(3);
        return registration;
    }
}
