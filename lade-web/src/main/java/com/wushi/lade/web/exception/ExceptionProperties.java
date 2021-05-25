package com.wushi.lade.web.exception;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wushi
 * @date 2020/3/31 9:54
 * @description
 */
@Configuration
@ConfigurationProperties(prefix = "wushi.exception", ignoreUnknownFields = false)
public class ExceptionProperties {

    /**
     * 是否启用url debug参数控制显示异常堆栈信息，控制单个url
     */
    private boolean enableUrlDebug;

    public boolean isEnableUrlDebug() {
        return enableUrlDebug;
    }

    public void setEnableUrlDebug(boolean enableUrlDebug) {
        this.enableUrlDebug = enableUrlDebug;
    }
}
