package com.wushi.lade.logging.mix;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import com.wushi.lade.common.enums.ErrorCode;
import com.wushi.lade.common.exceptions.BusinessException;
import com.wushi.lade.common.utils.JsonUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wushi
 * @date 2021/1/26 10:21
 */
public class MixAppender extends AppenderBase<LoggingEvent> {

    /**
     * 时间格式（纳秒）
     */
    private static final String TIME_FORMAT = "yyyyMMddHHmmssSSSSSSSSS";
    private static final String LADE = "lade";
    /**
     * 错误级别对应的优先级
     */
    private static final Map<Integer, String> LEVEL_MAP = new HashMap<Integer, String>() {
        {
            put(Level.ERROR_INT, "high");
            put(Level.WARN_INT, "middle");
        }
    };
    /**
     * 当前环境
     */
    private static final String PROD_ENV = "prod";
    /**
     * 当前IP
     */
    private static String ip;

    /**
     * 当前jar包版本号
     */
    private static String jarVersion;

    /**
     * 日志格式
     */
    private PatternLayoutEncoder encoder;
    /**
     * 日志提交地址
     */
    private String mixEndpoint;
    private String mixEndpointKey;
    /**
     * http请求timeout
     */
    private Integer mixTimeout;
    /**
     * 当前环境
     */
    private String mixEnv;
    private String mixCategory;
    /**
     * 客户编号
     */
    private String mixCustomerId;
    /**
     * 项目编号
     */
    private String mixProjectId;

    public void setEncoder(PatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }

    public void setMixEndpoint(String mixEndpoint) {
        this.mixEndpoint = mixEndpoint;
    }

    public void setMixCategory(String mixCategory) {
        this.mixCategory = mixCategory;
    }

    public void setMixCustomerId(String mixCustomerId) {
        this.mixCustomerId = mixCustomerId;
    }

    public void setMixProjectId(String mixProjectId) {
        this.mixProjectId = mixProjectId;
    }

    public void setMixEndpointKey(String mixEndpointKey) {
        this.mixEndpointKey = mixEndpointKey;
    }

    public void setMixTimeout(Integer mixTimeout) {
        this.mixTimeout = mixTimeout;
    }

    public void setMixEnv(String mixEnv) {
        this.mixEnv = mixEnv;
    }

    static {
        try (final DatagramSocket socket = new DatagramSocket()) {
            //本机ip
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        if (this.encoder == null) {
            addError("No encoder set for the appender named [" + name + "].");
            return;
        }
        if (StringUtils.isEmpty(this.mixEndpoint)) {
            addError("No mixUrl set for the appender named [" + name + "].");
            return;
        }
        if (StringUtils.isEmpty(this.mixCustomerId)) {
            addError("No customerId set for the appender named [" + name + "].");
            return;
        }
        if (StringUtils.isEmpty(this.mixProjectId)) {
            addError("No projectId set for the appender named [" + name + "].");
            return;
        }
        super.start();
    }

    @Override
    protected void append(LoggingEvent eventObject) {
        try {
            //初始化日志提交的内容
            LogContent logContent = new LogContent();
            logContent.setBatchId(LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
            logContent.setLevel(eventObject.getLevel().toString().toLowerCase());
            logContent.setTime(eventObject.getTimeStamp());
            logContent.setPriority(LEVEL_MAP.get(eventObject.getLevel().levelInt));
            String category = StringUtils.isEmpty(mixCategory) ? LADE : mixCategory;
            logContent.setCategory(category);
            logContent.setContent("");
            logContent.setTags(Collections.EMPTY_LIST);
            logContent.setRemark("");
            String evn = StringUtils.isEmpty(this.mixEnv) ? PROD_ENV : this.mixEnv;
            logContent.setEnv(evn);
            //日志来源
            LogSource logSource = new LogSource();
            logSource.setFrom(LADE);
            logSource.setName(LADE);
            logSource.setIp(ip);
            logSource.setLang("java");
            logSource.setVersion("1.2.0");
            logContent.setSource(logSource);
            //日志标识
            LogIdentity logIdentity = new LogIdentity();
            logIdentity.setCustomerId(this.mixCustomerId);
            logIdentity.setProjectId(this.mixProjectId);
            logIdentity.setTargetIp(ip);
            logContent.setIdentity(logIdentity);
            //如果是warn类型只记录message，异常类型要记录异常相关信息
            if (eventObject.getLevel().levelInt == Level.WARN_INT) {
                Map<String, String> map = new HashMap<String, String>() {{
                    put("errorText", eventObject.getFormattedMessage());
                }};
                logContent.setRawData(map);
            } else if (eventObject.getLevel().levelInt == Level.ERROR_INT) {
                //MDC请求跟踪
                String traceId = eventObject.getMDCPropertyMap().getOrDefault("traceId", "");
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setHttpTraceId(traceId);
                //获取异常相关信息
                if (eventObject.getThrowableProxy() != null) {
                    ThrowableProxy throwableProxy = (ThrowableProxy) eventObject.getThrowableProxy();
                    String stackTrace = getStackTrace(throwableProxy.getThrowable());
                    errorMessage.setStack(stackTrace);
                    //错误信息
                    if (throwableProxy.getThrowable() instanceof BusinessException) {
                        BusinessException businessException = (BusinessException) throwableProxy.getThrowable();
                        errorMessage.setMessage(businessException.getMessage());
                        errorMessage.setErrorCode(businessException.getErrorCode());
                        errorMessage.setErrorName(businessException.getErrorName());
                        errorMessage.setErrorText(businessException.getErrorText());
                    } else {
                        errorMessage.setErrorText(ErrorCode.Http_InternalServerError.getErrorText());
                        errorMessage.setErrorName(ErrorCode.Http_InternalServerError.toString());
                        errorMessage.setErrorCode(ErrorCode.Http_InternalServerError.getErrorCode());
                    }
                }
                logContent.setRawData(errorMessage);
            }
            String json = JsonUtils.toJSONString(logContent);
            sendHttpPost(json);
            System.out.println("运维监控日志提交完成");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 获取异常堆栈
     *
     * @param throwable
     * @return
     */
    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    /**
     * 异步执行提交操作
     *
     * @param jsonBody
     * @throws Exception
     */
    private void sendHttpPost(String jsonBody) throws Exception {
        int timeout = this.mixTimeout == null ? 5000 : this.mixTimeout;
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .build();
        HttpPost httpPost = new HttpPost(this.mixEndpoint);
        httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setEntity(new StringEntity(jsonBody, Charset.forName("UTF-8")));
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        httpClient.start();
        httpClient.execute(httpPost, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {
                try {
                    String responseContent = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                    System.out.println(responseContent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void failed(Exception e) {
                System.out.println(httpPost.getRequestLine() + "->" + e);
                System.out.println(" callback thread id is : " + Thread.currentThread().getId());
            }

            @Override
            public void cancelled() {
                System.out.println(httpPost.getRequestLine() + " cancelled");
                System.out.println(" callback thread id is : " + Thread.currentThread().getId());
            }
        });


    }
}
