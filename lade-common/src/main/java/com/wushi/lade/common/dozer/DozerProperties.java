package com.wushi.lade.common.dozer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wushi
 * @date 2020/12/16 13:59
 */
@ConfigurationProperties(prefix = "wushi.dozer")
@Component
public class DozerProperties {

    /**
     * 自定义转换类型
     */
    private List<String> mappingFile;

    public List<String> getMappingFile() {
        return mappingFile;
    }

    public void setMappingFile(List<String> mappingFile) {
        this.mappingFile = mappingFile;
    }
}
