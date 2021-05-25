package com.wushi.lade.common.utils;

import com.wushi.lade.common.exceptions.BusinessException;
import com.wushi.lade.common.interfaces.BaseErrorCode;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @author wushi
 * @date 2020/8/4 16:21
 */
public class Assert {
    protected Assert() {
    }

    public static void gtZero(Integer num, BaseErrorCode errorCode) {
        if (num == null || num <= 0) {
            fail(errorCode);
        }
    }

    public static void gtZero(Integer num, BaseErrorCode errorCode, Integer httpStatus) {
        if (num == null || num <= 0) {
            fail(errorCode, httpStatus);
        }
    }

    public static void gtZero(Long num, BaseErrorCode errorCode) {
        if (num == null || num.compareTo(0L) <= 0) {
            fail(errorCode);
        }
    }

    public static void gtZero(Long num, BaseErrorCode errorCode, Integer httpStatus) {
        if (num == null || num.compareTo(0L) <= 0) {
            fail(errorCode, httpStatus);
        }
    }

    public static void geZero(Integer num, BaseErrorCode errorCode) {
        if (num == null || num < 0) {
            fail(errorCode);
        }
    }

    public static void geZero(Integer num, BaseErrorCode errorCode, Integer httpStatus) {
        if (num == null || num < 0) {
            fail(errorCode, httpStatus);
        }
    }

    public static void gt(Integer num1, Integer num2, BaseErrorCode errorCode) {
        if (num1 <= num2) {
            fail(errorCode);
        }
    }

    public static void gt(Integer num1, Integer num2, BaseErrorCode errorCode, Integer httpStatus) {
        if (num1 <= num2) {
            fail(errorCode, httpStatus);
        }
    }

    public static void ge(Integer num1, Integer num2, BaseErrorCode errorCode) {
        if (num1 < num2) {
            fail(errorCode);
        }
    }

    public static void ge(Integer num1, Integer num2, BaseErrorCode errorCode, Integer httpStatus) {
        if (num1 < num2) {
            fail(errorCode, httpStatus);
        }
    }

    public static void eq(Object obj1, Object obj2, BaseErrorCode errorCode) {
        if (!obj1.equals(obj2)) {
            fail(errorCode);
        }
    }

    public static void eq(Object obj1, Object obj2, BaseErrorCode errorCode, Integer httpStatus) {
        if (!obj1.equals(obj2)) {
            fail(errorCode, httpStatus);
        }
    }

    public static void isTrue(boolean condition, BaseErrorCode errorCode) {
        if (condition) {
            fail(errorCode);
        }
    }

    public static void isTrue(boolean condition, BaseErrorCode errorCode, Integer httpStatus) {
        if (condition) {
            fail(errorCode, httpStatus);
        }
    }

    public static void isFalse(boolean condition, BaseErrorCode errorCode) {
        if (!condition) {
            fail(errorCode);
        }
    }

    public static void isFalse(boolean condition, BaseErrorCode errorCode, Integer httpStatus) {
        if (!condition) {
            fail(errorCode, httpStatus);
        }
    }

    public static void isEmpty(String condition, BaseErrorCode errorCode) {
        if (StringUtils.isEmpty(condition)) {
            fail(errorCode);
        }
    }

    public static void isEmpty(String condition, BaseErrorCode errorCode, Integer httpStatus) {
        if (StringUtils.isEmpty(condition)) {
            fail(errorCode, httpStatus);
        }
    }

    public static void isEmpty(Collection<?> condition, BaseErrorCode errorCode) {
        if (CollectionUtils.isEmpty(condition)) {
            fail(errorCode);
        }
    }

    public static void isEmpty(Collection<?> condition, BaseErrorCode errorCode, Integer httpStatus) {
        if (CollectionUtils.isEmpty(condition)) {
            fail(errorCode, httpStatus);
        }
    }

    public static void isNull(Object condition, BaseErrorCode errorCode) {
        if (condition == null) {
            fail(errorCode);
        }
    }

    public static void isNull(BaseErrorCode errorCode, Object... conditions) {
        if (ObjectUtils.isEmpty(conditions)) {
            fail(errorCode);
        }
    }

    public static void fail(BaseErrorCode errorCode) {
        throw new BusinessException(errorCode);
    }

    public static void fail(BaseErrorCode errorCode, Integer httpStatus) {
        throw new BusinessException(errorCode, httpStatus);
    }

    public static void fail(boolean condition, BaseErrorCode errorCode) {
        if (condition) {
            fail(errorCode);
        }
    }

    public static void fail(boolean condition, BaseErrorCode errorCode, Integer httpStatus) {
        if (condition) {
            fail(errorCode, httpStatus);
        }
    }

    public static void fail(String message) {
        throw new BusinessException(message);
    }

    public static void fail(boolean condition, String message) {
        if (condition) {
            fail(message);
        }
    }
}
