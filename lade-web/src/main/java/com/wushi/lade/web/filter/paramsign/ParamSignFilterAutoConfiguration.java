package com.wushi.lade.web.filter.paramsign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author wushi
 * @date 2020/3/17 14:23
 * @description
 */
@ConditionalOnProperty(value = "wushi.security.param.sign.enabled", havingValue = "true")
public class ParamSignFilterAutoConfiguration {

    @Autowired
    private ParamSignProperties properties;

    @Bean
    public FilterRegistrationBean<ParamSignFilter> paramSignFilterRegistrationBean() {
        FilterRegistrationBean<ParamSignFilter> registration = new FilterRegistrationBean<>();
        ParamSignFilter paramSignFilter = new ParamSignFilter(properties);
        registration.setFilter(paramSignFilter);
        registration.addUrlPatterns("/*");
        registration.setName("paramSignFilter");
        registration.setOrder(4);
        return registration;
    }
}
