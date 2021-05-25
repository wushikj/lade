package com.wushi.lade.web.filter.exception;

import com.wushi.lade.web.filter.request.RequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.util.Objects;

/**
 * 过滤器异常处理
 *
 * @author wushi
 * @date 2020/2/5 14:14
 */
public class ExceptionFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

    private final static String ERROR_RETHROW = "/error/filter";

    private final static String REQUEST_EXCEPTION = "request_exception";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("Exception Filter Init .......");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 是否已经放有异常栈, 避免循环
        boolean isRethrow = !Objects.isNull(request.getAttribute(REQUEST_EXCEPTION));
        if (isRethrow) {
            chain.doFilter(request, response);
            return;
        }
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            // 发生异常，保存异常栈
            request.setAttribute(REQUEST_EXCEPTION, e);
            request.getRequestDispatcher(ERROR_RETHROW).forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
