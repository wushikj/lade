package com.wushi.lade.web.entity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wushi
 * @date 2020/6/5 15:52
 */
public class IpBlockedInfo {
    /**
     * 请求ip
     */
    private String IP;
    /**
     * 请求信息
     */
    private HttpServletRequest httpServletRequest;

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
}
