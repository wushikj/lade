package com.wushi.lade.web.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 跨域Web路由配置
 *
 * @author wushi
 */
@Configuration
public class CorsWebMvcConfigurer implements WebMvcConfigurer {

    private CorsProperties corsProperties;

    @Resource(type = CorsProperties.class)
    public void setCorsProperties(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (!corsProperties.isEnabled()) {
            return;
        }
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
//                .allowCredentials(true);
    }
}
