package com.wushi.lade.web.cors;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 跨域属性
 *
 * @author wushi
 */
@Configuration
@ConfigurationProperties(prefix = "wushi.security.cors", ignoreUnknownFields = false)
public class CorsProperties {

    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
