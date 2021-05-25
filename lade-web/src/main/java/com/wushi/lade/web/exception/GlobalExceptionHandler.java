package com.wushi.lade.web.exception;

import com.wushi.lade.common.enums.ErrorCode;
import com.wushi.lade.common.exceptions.BusinessException;
import com.wushi.lade.common.utils.ExceptionUtils;
import com.wushi.lade.web.result.R;
import com.wushi.lade.web.result.model.ErrorModel;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;

/**
 * 全局异常处理，处理可预见的异常
 *
 * @author wushi
 */
@Slf4j
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active:dev}")
    private String active;

    @Autowired
    private ExceptionProperties properties;

    @Autowired(required = false)
    private HandleSpecialException handleSpecialException;

    /**
     * 开发环境
     */
    private static final String DEV_ENVIRONMENT = "dev";

    /**
     * 生产环境
     */
    private static final String PROD_ENVIRONMENT = "prod";

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleError(MissingServletRequestParameterException e) {
        log.warn("缺少必要的请求参数: {}", e.getMessage());
        String message = String.format("缺少必要的请求参数: %s", e.getParameterName());
        R errorResult = R.fail(ErrorCode.Http_RequiredParameter, message, HttpStatus.BAD_REQUEST);
        if (checkIsOpenStackPrint()) {
            errorResult.getError().setStack(ExceptionUtils.buildErrorMessage(e));
        }
        return errorResult;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleError(MethodArgumentTypeMismatchException e) {
        log.warn("请求参数格式错误：{}", e.getMessage());
        String message = String.format("请求参数格式错误: %s", e.getName());
        R errorResult = R.fail(ErrorCode.Http_NotAllowedParameter, message, HttpStatus.BAD_REQUEST);
        if (checkIsOpenStackPrint()) {
            errorResult.getError().setStack(ExceptionUtils.buildErrorMessage(e));
        }
        return errorResult;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleError(MethodArgumentNotValidException e) {
        log.warn("参数验证失败：{}", e.getMessage());
        return handleError(e.getBindingResult(), e);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleError(BindException e) {
        log.warn("参数绑定失败：{}", e.getMessage());
        return handleError(e.getBindingResult(), e);
    }

    private R handleError(BindingResult result, Exception e) {
        FieldError error = result.getFieldError();
        String message = String.format("%s: %s", error.getField(), error.getDefaultMessage());
        R errorResult = R.fail(ErrorCode.Http_RequiredParameterBind, message, HttpStatus.BAD_REQUEST);
        if (checkIsOpenStackPrint()) {
            errorResult.getError().setStack(ExceptionUtils.buildErrorMessage(e));
        }
        return errorResult;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleError(ConstraintViolationException e) {
        log.warn("参数验证失败：{}", e.getMessage());
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String message = String.format("%s:%s", path, violation.getMessage());
        R errorResult = R.fail(ErrorCode.Http_RequiredCheck, message, HttpStatus.BAD_REQUEST);
        if (checkIsOpenStackPrint()) {
            errorResult.getError().setStack(ExceptionUtils.buildErrorMessage(e));
        }
        return errorResult;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleError(HttpMessageNotReadableException e) {
        log.error("消息不能读取: {}", e.getMessage());
        String message = String.format("消息不能读取: %s", e.getMessage());
        R errorResult = R.fail(ErrorCode.Http_RequiredNotRead, message, HttpStatus.BAD_REQUEST);
        if (checkIsOpenStackPrint()) {
            errorResult.getError().setStack(ExceptionUtils.buildErrorMessage(e));
        }
        return errorResult;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R handleError(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法: {}", e.getMessage());
        String message = String.format("不支持当前请求方法: %s", e.getMessage());
        R errorResult = R.fail(ErrorCode.Http_MethodNotAllowed, message, HttpStatus.METHOD_NOT_ALLOWED);
        if (checkIsOpenStackPrint()) {
            errorResult.getError().setStack(ExceptionUtils.buildErrorMessage(e));
        }
        return errorResult;

    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public R handleError(HttpMediaTypeNotSupportedException e) {
        log.error("不支持当前媒体类型: {}", e.getMessage());
        String message = String.format("不支持当前媒体类型: %s", e.getMessage());
        return R.fail(ErrorCode.Http_UnsupportedMediaType, message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleError(BusinessException e) {
        log.error("业务异常", e);
        String message = String.format("业务异常: %s", e.getMessage());
        R errorResult = R.fail(ErrorCode.Http_InternalServerError, message, HttpStatus.INTERNAL_SERVER_ERROR);
        if (checkIsOpenStackPrint()) {
            errorResult.getError().setStack(ExceptionUtils.buildErrorMessage(e));
        }
        return errorResult;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleError(Throwable e) {
        log.error("服务器异常", e);
        String message = String.format("服务器异常: %s", e.getMessage());
        R errorResult = R.fail(ErrorCode.Http_InternalServerError, message, HttpStatus.INTERNAL_SERVER_ERROR);
        if (checkIsOpenStackPrint()) {
            errorResult.getError().setStack(ExceptionUtils.buildErrorMessage(e));
        }
        return errorResult;
    }

    /**
     * 拦截捕捉自定义异常 Exception.class
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exception(Exception exception) {
        log.error("自定义异常", exception);
        R apiResult = null;
        //有的自定义异常会被包装成其他异常，导致全局异常处理无法精准处理
        if (this.handleSpecialException != null) {
            apiResult = this.handleSpecialException.hanlde(exception);
        }
        if (apiResult == null) {
            apiResult = new R();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorModel.setErrorCode(ErrorCode.Http_InternalServerError.getErrorCode());
            errorModel.setErrorName(ErrorCode.Http_InternalServerError.name());
            errorModel.setErrorText(ErrorCode.Http_InternalServerError.getErrorText());
            apiResult.setError(errorModel);
        }
        stackControl(apiResult, exception);
        return apiResult;
    }

    private void stackControl(R apiResult, Exception ex) {
        if (checkIsOpenStackPrint()) {
            apiResult.getError().setStack(ExceptionUtils.buildErrorMessage(ex));
            apiResult.getError().setMessage(ex.getMessage());
        }
    }

    private boolean checkIsOpenStackPrint() {
        // 获取上下文的debug参数，如果有且为true，则输出堆栈信息
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        boolean debug = Boolean.parseBoolean(request.getParameter("debug"));
        //如果是开发环境就显示堆栈信息，生产环境下可通过url参数控制是否显示堆栈
        boolean isDev = active.equalsIgnoreCase(DEV_ENVIRONMENT);
        boolean isProd = active.equalsIgnoreCase(PROD_ENVIRONMENT);
        boolean showStackByUrlDebug = isProd && properties.isEnableUrlDebug() && debug;
        return isDev || showStackByUrlDebug;
    }

}

