package com.wushi.lade.web.result;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

/**
 * 返回文本
 *
 * @author wushi
 * @date 2020/3/19 16:32
 * @description
 */
public class ContentResult {

    /**
     * 返回的文本内容
     **/
    private String content;

    /**
     * mime类型
     **/
    private String contentType;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return StringUtils.isBlank(contentType) ? MediaType.TEXT_PLAIN_VALUE : contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ContentResult() {
    }

    public ContentResult(String content, String contentType) {
        this.content = content;
        this.contentType = contentType;
    }
}
