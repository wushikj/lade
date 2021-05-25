package com.wushi.lade.common.utils;

/**
 * 异常信息堆栈打印工具类
 *
 * @author wushi
 * @date 2020/2/5 16:40
 */

public class ExceptionUtils {
    /**
     * 打印异常堆栈信息
     *
     * @param ex
     * @return java.lang.String
     * @author wushi
     * @date 2020/2/5 16:40
     */
    public static String getStackTraceString(Throwable ex) {
        StackTraceElement[] traceElements = ex.getStackTrace();
        StringBuilder traceBuilder = new StringBuilder();
        if (traceElements != null && traceElements.length > 0) {
            for (StackTraceElement traceElement : traceElements) {
                traceBuilder.append(traceElement.toString());
                traceBuilder.append("\n");
            }
        }
        return traceBuilder.toString();
    }

    /**
     * 构造异常堆栈信息
     *
     * @param ex
     * @return java.lang.String
     * @author wushi
     * @date 2020/2/5 16:41
     */
    public static String buildErrorMessage(Throwable ex) {
        String result;
        String stackTrace = getStackTraceString(ex);
        String exceptionType = ex.toString();
        result = String.format("%s \r\n %s", exceptionType, stackTrace);
        return result;
    }
}
