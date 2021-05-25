package com.wushi.lade.web.filter.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author wushi
 */
@Configuration
@EnableConfigurationProperties({MaliciousFileFilterProperties.class})
public class MaliciousFileAutoConfiguration {

    private MaliciousFileFilterProperties maliciousFileFilterProperties;

    @Autowired
    public void setMaliciousFileFilterProperties(MaliciousFileFilterProperties maliciousFileFilterProperties) {
        this.maliciousFileFilterProperties = maliciousFileFilterProperties;
    }

    @Bean
    public FilterRegistrationBean<MaliciousFileFilter> maliciousFileFilterRegistration() {
        FilterRegistrationBean<MaliciousFileFilter> registration = new FilterRegistrationBean<>();
        MaliciousFileFilter maliciousFileFilter = new MaliciousFileFilter();
        maliciousFileFilter.setMaliciousFileFilterProperties(maliciousFileFilterProperties);
        registration.setFilter(maliciousFileFilter);
        registration.addUrlPatterns("/*");
        registration.setName("maliciousFileFilter");
        registration.setOrder(10);
        return registration;
    }
}