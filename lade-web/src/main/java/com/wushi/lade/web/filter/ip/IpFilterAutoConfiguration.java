package com.wushi.lade.web.filter.ip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author wushi
 */
@Configuration
@EnableConfigurationProperties({IpFilterProperties.class})
public class IpFilterAutoConfiguration {

    private IpFilterProperties ipFilterProperties;

    @Autowired
    public void setIpFilterProperties(IpFilterProperties ipFilterProperties) {
        this.ipFilterProperties = ipFilterProperties;
    }

    @Bean
    public FilterRegistrationBean<IpFilter> ipFilterRegistration() throws Exception {
        FilterRegistrationBean<IpFilter> registration = new FilterRegistrationBean<>();
        IpFilter filter = new IpFilter();
        filter.setIpFilterProperties(ipFilterProperties);
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName("ipFilter");
        registration.setOrder(2);
        return registration;
    }
}
