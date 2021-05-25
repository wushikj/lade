package com.wushi.lade.web.filter.paramsign;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.wushi.lade.common.enums.ErrorCode;
import com.wushi.lade.common.exceptions.BusinessException;
import com.wushi.lade.security.MD5;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.TreeMap;

/**
 * @author wushi
 * @date 2020/3/16 14:24
 * @description
 */
public class ParamSignFilter implements Filter {

    private static final String X_Lade_WebApi_Timestamp = "X-Lade-WebApi-Timestamp";
    private static final String X_Lade_WebApi_Content_MD5 = "X-Lade-WebApi-Content-MD5";
    private static final String X_Lade_WebApi_Signature = "X-Lade-WebApi-Signature";
    private static final String X_Lade_WebApi_Nonce = "X-Lade-WebApi-Nonce";

    private ParamSignProperties properties;

    private Logger logger;

    public ParamSignFilter(ParamSignProperties properties) {
        this.properties = properties;
        logger = LoggerFactory.getLogger(ParamSignFilter.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        //判断是否需要进行参数验证
        if (properties.getExclude() != null && properties.getExclude().contains(servletRequest.getRequestURI())) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            CachedBodyHttpServletRequestWrapper requestWrapper = new CachedBodyHttpServletRequestWrapper(servletRequest);
            signature(requestWrapper);
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    /**
     * 参数签名验证
     *
     * @param request
     * @return SignatureResult
     * @author wushi
     * @date 2020/3/16 14:33
     * @description
     */
    private void signature(HttpServletRequest request) throws IOException {
        //从客户端提交的时间戳转换为DateTime
        String timestamp = request.getHeader(X_Lade_WebApi_Timestamp);
        if (StringUtils.isBlank(timestamp)) {
            throw new BusinessException(ErrorCode.Sign_X_Lade_WebApi_Timestamp_Header_Required, HttpStatus.BAD_REQUEST.value());
        }
        Date dateTime = timeStamp2Date(timestamp);
        Date now = new Date();
        Long expireSecond = properties.getExpireSecond();
        Long totalSecond = (now.getTime() - dateTime.getTime()) / 1000;
        //前后端时间不一致的时候，允许的误差值为1分钟
        if (totalSecond.compareTo(-60L) < 0 || totalSecond.compareTo(expireSecond) > 0) {
            throw new BusinessException(ErrorCode.Sign_SignatureTimestampExpired, HttpStatus.BAD_REQUEST.value());
        }

        //如果要防止重放攻击,需要判断一段时间内,X-Lade-WebApi-Nonce是否提交过
        String nonce = request.getHeader(X_Lade_WebApi_Nonce);
        if (StringUtils.isBlank(nonce)) {
            throw new BusinessException(ErrorCode.Sign_X_Lade_WebApi_Nonce_Header_Required, HttpStatus.BAD_REQUEST.value());
        }

        String clientSignature = request.getHeader(X_Lade_WebApi_Signature);
        if (StringUtils.isBlank(clientSignature)) {
            throw new BusinessException(ErrorCode.Sign_X_Lade_WebApi_Signature_Header_Required, HttpStatus.BAD_REQUEST.value());
        }

        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        //非get请求可能存在没传body的情况
        if (StringUtils.isNotBlank(body)) {
            String contentMD5 = request.getHeader(X_Lade_WebApi_Content_MD5);
            if (StringUtils.isBlank(contentMD5)) {
                throw new BusinessException(ErrorCode.Sign_X_Lade_WebApi_Content_MD5_Header_Required, HttpStatus.BAD_REQUEST.value());
            }


            if (!request.getContentType().equals("application/json")) {
                throw new BusinessException(ErrorCode.Sign_OnlyJsonSupport, HttpStatus.BAD_REQUEST.value());
            }


            //验证Body摘要,字符集统一用UTF-8
            checkBody(contentMD5, body);
        }

        //提取需要参加签名的参数
        TreeMap<String, String> parameters = sortedRequestParameters(request, body);
        //拼接参数
        String stringToSign = request.getMethod() + request.getRequestURI() + getStringToSign(parameters);
        stringToSign = stringToSign.toLowerCase();
        String secret = properties.getSecret();
        //参数签名
        String signature = sign(secret, stringToSign);
        if (!clientSignature.equals(signature)) {
            //参数签名不一致
            throw new BusinessException(ErrorCode.Sign_SignatureError, HttpStatus.BAD_REQUEST.value());
        }
    }


    /**
     * 从请求中提取头部和url参数参数
     *
     * @param request
     * @param requestBody
     * @return java.util.TreeMap<java.lang.String, java.lang.String>
     * @author wushi
     * @date 2020/3/18 9:45
     * @description
     */
    private TreeMap<String, String> sortedRequestParameters(HttpServletRequest request, String requestBody) throws IOException {

        TreeMap<String, String> result = new TreeMap<>();
        if (StringUtils.isNotEmpty(requestBody)) {
            result.put(X_Lade_WebApi_Content_MD5.toLowerCase(), request.getHeader(X_Lade_WebApi_Content_MD5));
        }

        result.put(X_Lade_WebApi_Timestamp.toLowerCase(), request.getHeader(X_Lade_WebApi_Timestamp));
        result.put(X_Lade_WebApi_Nonce.toLowerCase(), request.getHeader(X_Lade_WebApi_Nonce));

        //前端的参数有可能是编码过的，直接用Query的话会不一致
        if (StringUtils.isNoneBlank(request.getQueryString())) {
            String[] queries = request.getQueryString().split("&");
            for (String item : queries) {
                String[] tmp = item.split("=");
                result.put(tmp[0], tmp.length == 2 ? tmp[1] : "");
            }
        }

        return result;
    }

    /**
     * 将排序后的参数,按照keyvalue的形式拼接到一起
     *
     * @param parameters
     * @return java.lang.String
     * @author wushi
     * @date 2020/3/18 9:45
     * @description
     */
    private String getStringToSign(TreeMap<String, String> parameters) {
        StringBuilder builder = new StringBuilder();
        for (String parameter : parameters.keySet()) {
            builder.append(parameter);
            builder.append(parameters.get(parameter));
        }

        return builder.toString();
    }

    /**
     * 参数签名
     *
     * @param secret
     * @param stringToSign
     * @return java.lang.String
     * @author wushi
     * @date 2020/3/18 9:45
     * @description
     */
    private String sign(String secret, String stringToSign) {

        String base64 = Base64.getEncoder().encodeToString(stringToSign.getBytes());
        String hmacSha1 = DigestUtil.hmac(HmacAlgorithm.HmacSHA1, secret.getBytes()).digestHex(base64);
        String sign = MD5.md5Encrypt(hmacSha1).toLowerCase();
        return sign;
    }

    /**
     * 验证Body内容是否被篡改
     *
     * @param contentMd5
     * @param body
     * @return SignatureResult
     * @author wushi
     * @date 2020/3/18 9:43
     * @description
     */
    private void checkBody(String contentMd5, String body) throws IOException {

        //Body摘要的生成算法是Body的MD5值再Base64
        String bodyMD5 = MD5.md5Encrypt(body).toLowerCase();
        String bodyDigest = Base64.getEncoder().encodeToString(bodyMD5.getBytes());
        if (!contentMd5.equals(bodyDigest)) {
            throw new BusinessException(ErrorCode.Sign_BodyDigestError, HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public Date timeStamp2Date(String seconds) {
        return new Date(Long.valueOf(seconds));
    }
}
