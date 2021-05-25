package com.wushi.lade.web.filter.paramsign;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wushi
 * @date 2020/3/17 14:27
 * @description
 */
@Component
@ConfigurationProperties("wushi.security.param.sign")
public class ParamSignProperties {
    /**
     * 是否启用参数签名
     **/
    private Boolean enabled;
    /**
     * 参数签名加密密钥
     **/
    private String secret;

    /**
     * 签名过期时间，单位：秒
     **/
    private Long expireSecond;

    /**
     * 不需要签名就可以访问的接口，以“/”开头，多个以“,”分隔
     **/
    private List<String> exclude;

    public Boolean getEnabled() {
        return enabled == null ? false : enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getSecret() {
        return secret == null ? "lade" : secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpireSecond() {
        return expireSecond == null ? 60L : expireSecond;
    }

    public void setExpireSecond(Long expireSecond) {
        this.expireSecond = expireSecond;
    }

    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }
}
