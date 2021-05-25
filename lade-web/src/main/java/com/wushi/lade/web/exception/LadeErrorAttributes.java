/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wushi.lade.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wushi.lade.common.enums.ErrorCode;
import com.wushi.lade.common.exceptions.BusinessException;
import com.wushi.lade.web.filter.jackson.JacksonJsonFilter;
import com.wushi.lade.web.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 全局异常处理
 *
 * @author wushi
 */
@Slf4j
public class LadeErrorAttributes extends DefaultErrorAttributes {

    private String active;

    public LadeErrorAttributes(String active) {
        this.active = active;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        String requestUri = this.getAttr(webRequest, "javax.servlet.error.request_uri");
        Integer status = this.getAttr(webRequest, "javax.servlet.error.status_code");
        Throwable error = getError(webRequest);
        R result;
        if (error == null && "/".equals(requestUri)) {
            log.error("URL:{} ,Error status:{}", requestUri, status);
            result = R.fail(ErrorCode.Http_NotFound, ErrorCode.Http_NotFound.getErrorText(), HttpStatus.NOT_FOUND);
        } else if (error == null) {
            log.error("URL:{} ,Error status:{}", requestUri, status);
            result = R.fail(ErrorCode.UnknownError, "系统未知异常[HttpStatus]:" + status, HttpStatus.valueOf(status));
        } else {
            log.error(String.format("URL:%s ,Error status:%d", requestUri, status), error);
            result = R.fail(ErrorCode.Http_InternalServerError, error.getMessage(), HttpStatus.valueOf(status));
        }
        String jsonStr;
        // 返回成功消息
        if (result.isSuccess()) {
            // Null值字段是否返回
            JsonInclude.Include include = result.getIncludeNullField() != null ? result.getIncludeNullField() : showNullField();
            jsonStr = filter(result, include, getOkJsonFilter());
        } else {
            // 返回错误信息
            jsonStr = filter(result, JsonInclude.Include.ALWAYS, getFailJsonFilter(result.getError().getStack()));
        }
        return readValue(jsonStr, Map.class);
    }

    @Nullable
    private <T> T getAttr(WebRequest webRequest, String name) {
        return (T) webRequest.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * Json string to object
     *
     * @param jsonStr
     * @param valueType
     */
    private static <T> T readValue(String jsonStr, Class<T> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        List<String> lists = new ArrayList<>(Arrays.asList("error", "success", "timestamp"));
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
        Boolean isDev = active.equalsIgnoreCase("dev");
        Boolean showNullValueByUrlDebug = isDev || debug;
        return showNullValueByUrlDebug ? JsonInclude.Include.ALWAYS : null;
    }

}
