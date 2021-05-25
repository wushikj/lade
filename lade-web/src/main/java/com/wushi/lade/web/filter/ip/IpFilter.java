package com.wushi.lade.web.filter.ip;


import com.wushi.lade.common.enums.ErrorCode;
import com.wushi.lade.common.exceptions.BusinessException;
import com.wushi.lade.web.entity.IpBlockedInfo;
import com.wushi.lade.web.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wushi
 */
public class IpFilter implements Filter {

    private static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

    @Autowired(required = false)
    private IpBlockedLogAppender ipBlockedLogAppender;

    private IpFilterProperties ipFilterProperties;
    private IpCache ipCache;

    public void setIpFilterProperties(IpFilterProperties ipFilterProperties) throws Exception {
        this.ipFilterProperties = ipFilterProperties;
        ipCache = new IpCache();
        ipCache.initIp(ipFilterProperties);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        switch (ipFilterProperties.getMode()) {
            case NONE:
                //不做处理
                filterChain.doFilter(request, response);
                break;
            case ALLOWED:
                //白名单，读取wushi.security.ip-filter.allowed配置
                filterByAllowedList(filterChain, (HttpServletRequest) request, (HttpServletResponse) response, ipFilterProperties.getAllowed());
                break;
            case BLOCKED:
                //黑名单，读取wushi.security.ip-filter.blocked配置
                filterByBlockedList(filterChain, (HttpServletRequest) request, (HttpServletResponse) response, ipFilterProperties.getBlocked());
                break;
            default:
                break;
        }
    }

    private void filterByBlockedList(FilterChain filterChain, HttpServletRequest request, HttpServletResponse response,
                                     String blockListRules) throws IOException, ServletException {
        if (StringUtils.isEmpty(blockListRules)) {
            //所有均可见
            filterChain.doFilter(request, response);
            return;
        }
        String requestIp = IpUtils.getRealIp(request);
        if (ipCache.isContainsIp(requestIp)) {
            logBlocked(requestIp, request);
            throw new BusinessException(ErrorCode.Http_Forbidden, HttpStatus.FORBIDDEN.value());
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void filterByAllowedList(FilterChain filterChain, HttpServletRequest request, HttpServletResponse response,
                                     String allowedListRules) throws IOException, ServletException {
        if (StringUtils.isEmpty(allowedListRules)) {
            throw new BusinessException(ErrorCode.Http_Forbidden, HttpStatus.FORBIDDEN.value());
        }
        String requestIp = IpUtils.getRealIp(request);
        if (ipCache.isContainsIp(requestIp)) {
            filterChain.doFilter(request, response);
        } else {
            logBlocked(requestIp, request);
            throw new BusinessException(ErrorCode.Http_Forbidden, HttpStatus.FORBIDDEN.value());
        }
    }


    private void logBlocked(String requestIp, HttpServletRequest request) {
        if (ipBlockedLogAppender != null) {
            IpBlockedInfo ipBlockedInfo = new IpBlockedInfo();
            ipBlockedInfo.setIP(requestIp);
            ipBlockedInfo.setHttpServletRequest(request);
            ipBlockedLogAppender.doAppend(ipBlockedInfo);
        }
    }
}
