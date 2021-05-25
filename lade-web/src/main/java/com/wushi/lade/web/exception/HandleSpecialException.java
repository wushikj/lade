package com.wushi.lade.web.exception;

import com.wushi.lade.web.result.R;

/**
 * 全局异常处理扩展
 * 有的自定义异常会被包装成其他异常，导致全局异常处理无法精准处理
 *
 * @author wushi
 * @date 2020/11/23 15:31
 */
public interface HandleSpecialException {

    /**
     * 处理异常
     *
     * @param ex
     * @return
     */
    R hanlde(Exception ex);
}
