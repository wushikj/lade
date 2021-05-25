package com.wushi.lade.web.filter.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wushi
 */
@ConfigurationProperties(prefix = "wushi.security.malicious-file", ignoreUnknownFields = false)
public class MaliciousFileFilterProperties {

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 恶意文件类型补充。
     * <br/>
     * 多文件类型以','号间隔
     */
    private String type;
}
