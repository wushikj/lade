package com.wushi.lade.common.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * javabean映射工具配置
 *
 * @author wushi
 */
@Configuration
public class DozerBeanMapperConfigure {

    @Autowired
    private DozerProperties dozerProperties;

    @Bean
    public DozerBeanMapper mapper() {
        List<String> mappingFiles = new ArrayList();
        if (dozerProperties != null && !CollectionUtils.isEmpty(dozerProperties.getMappingFile())) {
            mappingFiles.addAll(dozerProperties.getMappingFile());
        } else {
            mappingFiles.add("dozerJdk8Converters.xml");
        }
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(mappingFiles);
        return mapper;
    }

    public BeanMappingBuilder beanMappingBuilder() {
        BeanMappingBuilder beanMappingBuilder = new BeanMappingBuilder() {
            @Override
            protected void configure() {

            }
        };
        return beanMappingBuilder;
    }
}
