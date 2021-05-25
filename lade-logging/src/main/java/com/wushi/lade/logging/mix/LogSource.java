package com.wushi.lade.logging.mix;

import java.io.Serializable;

/**
 * 日志来源
 *
 * @author wushi
 * @date 2021/1/26 15:04
 */
public class LogSource implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 日志来源
     */
    private String from;
    private String name;
    private String version;
    /**
     * 开发探针的语言
     */
    private String lang;
    /**
     * 探针所在主机Ip
     */
    private String ip;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "LogSource{" +
                "from='" + from + '\'' +
                ", source='" + name + '\'' +
                ", version='" + version + '\'' +
                ", lang='" + lang + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
