package com.wushi.lade.web.result.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wushi.lade.common.enums.ErrorCode;
import com.wushi.lade.common.exceptions.BusinessException;
import com.wushi.lade.web.filter.jackson.JacksonJsonFilter;
import com.wushi.lade.web.result.ContentResult;
import com.wushi.lade.web.result.FileResult;
import com.wushi.lade.web.result.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 自定义返回类型处理器
 *
 * @author wushi
 * @date 2020/3/19 10:05
 * @description
 */
public class ActionResultReturnValueHandler implements HandlerMethodReturnValueHandler, AsyncHandlerMethodReturnValueHandler {

    private String active;

    public ActionResultReturnValueHandler(String active) {
        this.active = active;
    }

    @Override
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        return supportsReturnType(returnType);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        //只处理继承自ActionResult接口的返回值
        Class clazz = ResolvableType.forMethodParameter(returnType).resolve();
        return R.class.isAssignableFrom(clazz);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        //设置响应内容编码
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        if (returnValue instanceof R) {
            // 返回json格式
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            R result = (R) returnValue;
            String jsonStr;
            // 返回成功消息
            if (result.isSuccess()) {
                // Null值字段是否返回
                JsonInclude.Include include = result.getIncludeNullField() != null ? result.getIncludeNullField() : showNullField();
                jsonStr = filter(result, include, getOkJsonFilter());
                response.getWriter().write(jsonStr);
            } else {
                // 返回错误信息
                jsonStr = filter(result, JsonInclude.Include.ALWAYS, getFailJsonFilter(result.getError().getStack()));
                // 可设置响应状态码
                response.setStatus(result.getError().getStatusCode());
                response.getWriter().write(jsonStr);
            }
        } else if (returnValue instanceof FileResult) {
            //返回文件
            FileResult fileResult = (FileResult) returnValue;
            response.reset();
            //有传文件名则下载文件，没传文件名会在浏览器中预览
            if (StringUtils.isNotBlank(fileResult.getFileName())) {
                response.addHeader("Content-Disposition", "attachment;filename=" + fileResult.getFileName());
            }
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType(fileResult.getContentType());
            outputStream.write(StreamUtils.copyToByteArray(fileResult.getFileInputStream()));
            outputStream.flush();
            outputStream.close();
        } else if (returnValue instanceof ContentResult) {
            ContentResult contentResult = (ContentResult) returnValue;
            response.setContentType(contentResult.getContentType());
            response.getWriter().write(contentResult.getContent());
        }
    }

    /**
     * 动态过滤json序列化字段
     *
     * @param apiResult  R实体
     * @param jsonFilter 序列规则
     */
    private <T> String filter(R<T> apiResult, JsonInclude.Include include, JacksonJsonFilter jsonFilter) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(include);
        mapper.setFilterProvider(jsonFilter);
        mapper.addMixIn(R.class, jsonFilter.getClass());
        if (apiResult.getSerializeConfig() != null) {
            mapper.setConfig(apiResult.getSerializeConfig());
        }
        if (apiResult.getMixIns() != null) {
            mapper.setMixInResolver(apiResult.getMixIns());
        }
        String json;
        try {
            json = mapper.writeValueAsString(apiResult);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.Json_SerializationFailure, e);
        }
        return json;
    }

    /**
     * 构造成功消息过滤
     */
    private JacksonJsonFilter getOkJsonFilter() {
        // include ["traceId", "timestamp", "success", "paging", "data"]
        JacksonJsonFilter jsonFilter = new JacksonJsonFilter();
        List<String> lists = new ArrayList<>(Arrays.asList("traceId", "timestamp", "success", "paging", "data"));
        jsonFilter.include(R.class, lists);
        return jsonFilter;
    }

    /**
     * 构造失败消息过滤
     */
    private JacksonJsonFilter getFailJsonFilter(String stack) {
        // include ["traceId", "message", "success", "timestamp", "method", "statusCode", "errorCode", "errorName", "errorText"]
        JacksonJsonFilter jsonFilter = new JacksonJsonFilter();
        List<String> lists = new ArrayList<>(Arrays.asList("traceId", "error", "success", "timestamp"));
        // 判断结果时候含有堆栈信息，如果有则返回的失败信息里需要带上stack字段
        if (org.springframework.util.StringUtils.hasText(stack)) {
            lists.add("stack");
        }
        jsonFilter.include(R.class, lists);
        return jsonFilter;
    }

    /**
     * 控制json串是否显示null值
     *
     * @return
     */
    private JsonInclude.Include showNullField() {
        //如果是开发环境就显示null值，生产环境可以通过url参数控制是否显示null值
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Boolean debug = Boolean.valueOf(request.getParameter("debug"));
        Boolean isDev = "dev".equalsIgnoreCase(active);
        Boolean showNullValueByUrlDebug = isDev || debug;
        return showNullValueByUrlDebug ? JsonInclude.Include.ALWAYS : null;
    }
}
