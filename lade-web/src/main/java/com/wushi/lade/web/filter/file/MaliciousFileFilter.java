package com.wushi.lade.web.filter.file;

import com.wushi.lade.common.enums.ErrorCode;
import com.wushi.lade.common.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author wushi
 */
public class MaliciousFileFilter implements Filter {
    private static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";
    private MaliciousFileCache maliciousFileCache;

    public void setMaliciousFileFilterProperties(MaliciousFileFilterProperties maliciousFileFilterProperties) {
        maliciousFileCache = new MaliciousFileCache();
        maliciousFileCache.addMaliciousFiles(maliciousFileFilterProperties.getType());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // 先实例化一个文件解析器
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(httpServletRequest.getSession()
                .getServletContext());
        // 判断request请求中是否有文件上传
        if (commonsMultipartResolver.isMultipart(httpServletRequest)) {
            // 转换Request
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(httpServletRequest);
            Iterator<String> fileNames = multipartRequest.getFileNames();
            while (fileNames.hasNext()) {
                //取出单个文件
                MultipartFile file = multipartRequest.getFile(fileNames.next());
                if (file == null) {
                    continue;
                }
                // 获得原始文件名
                String fileName = file.getOriginalFilename();
                if (StringUtils.isEmpty(fileName)) {
                    continue;
                }
                int lastIndex = fileName.lastIndexOf(".");
                if (lastIndex >= 0) {
                    //有后缀，获取后缀名
                    String fileExt = fileName.substring(lastIndex);
                    if (maliciousFileCache.getRetMaliciousFiles().contains(fileExt)) {
                        throw new BusinessException(ErrorCode.File_UnsupportFileFormat,HttpStatus.FORBIDDEN.value());
                    } else {
                        filterChain.doFilter(multipartRequest, servletResponse);
                    }
                }
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
