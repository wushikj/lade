package com.wushi.lade.web.filter.request;

import com.wushi.lade.common.utils.TraceIdGeneratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;


/**
 * Request过滤器，添加TraceId以及打印接口请求耗时
 *
 * @author wushi
 * @date 2020/1/17 9:12
 */
public class RequestFilter implements Filter {

    private final static Logger LOGGER  = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestTraceId = TraceIdGeneratorUtils.create();
        // 请求进入时间
        long start = System.currentTimeMillis();
        request.setAttribute("traceId", requestTraceId);
        MDC.put("traceId",requestTraceId);
        filterChain.doFilter(request, servletResponse);
        LOGGER.debug("traceId={} ,time={} ,method={} ,url={} ,duration={}ms", requestTraceId,
                LocalDateTime.now(), request.getMethod(), request.getRequestURL(),
                System.currentTimeMillis() - start);
    }

    @Override
    public void destroy() {

    }
}
