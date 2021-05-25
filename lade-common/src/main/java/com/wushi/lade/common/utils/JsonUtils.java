package com.wushi.lade.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.wushi.lade.common.enums.ErrorCode;
import com.wushi.lade.common.exceptions.BusinessException;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Jackson工具方法
 *
 * @author wushi
 * @date 2020/11/2 15:40
 */
public class JsonUtils {

    private static ObjectMapper mapper;

    private JsonUtils() {
    }

    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .registerModule(new ParameterNamesModule());
        //设置时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        mapper.setDateFormat(dateFormat);
        //设置大小写格式
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        //设置空值是否返回
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //设置序列化格式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置反序列化格式
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 获取序列化配置
     *
     * @return
     */
    public static SerializationConfig getSerializationConfig() {
        return mapper.copy().getSerializationConfig();
    }

    /**
     * 获取反序列化配置
     *
     * @return
     */
    public static DeserializationConfig getDeserializationConfig() {
        return mapper.copy().getDeserializationConfig();
    }

    /**
     * 获取ObjectMapper对象
     *
     * @return com.fasterxml.jackson.databind.ObjectMapper
     * @author wushi
     * @date 2020/11/3 17:33
     */
    public static ObjectMapper getInstance() {
        return mapper.copy();
    }

    /**
     * 将对象序列化为字符串
     *
     * @param object 要序列化的对象
     * @return java.lang.String
     * @author wushi
     * @date 2020/11/3 17:27
     */
    public static String toJSONString(Object object) {
        try {
            return toJSONString(object, null, null);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.Json_SerializationFailure, ex);
        }
    }

    /**
     * 将对象序列化为字符串
     *
     * @param object  要序列化的对象
     * @param include 自定义字段值Null时的返回策略
     * @return java.lang.String
     * @author wushi
     * @date 2020/11/3 17:28
     */
    public static String toJSONString(Object object, JsonInclude.Include include) {
        try {
            return toJSONString(object, include, null);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.Json_SerializationFailure, ex);
        }
    }

    /**
     * 将对象序列化为字符串
     *
     * @param object              要序列化的对象
     * @param include             自定义字段值Null时的返回策略
     * @param serializationConfig 设置序列化配置，JsonUtils.getSerializationConfig()
     * @return java.lang.String
     * @author wushi
     * @date 2020/11/3 17:28
     */
    public static String toJSONString(Object object, JsonInclude.Include include, SerializationConfig serializationConfig) {
        try {
            ObjectMapper mapper = getMapper(include, serializationConfig);
            return mapper.writeValueAsString(object);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.Json_SerializationFailure, ex);
        }
    }

    /**
     * 将对象序列化为字符串
     *
     * @param object              要序列化的对象
     * @param include             自定义字段值Null时的返回策略
     * @param serializationConfig 设置序列化配置，JsonUtils.getSerializationConfig()
     * @return java.lang.String
     * @author wushi
     * @date 2020/11/3 17:28
     */
    public static String toJSONString(Object object, JsonInclude.Include include, SerializationConfig serializationConfig, SimpleMixInResolver mixIns) {
        try {
            ObjectMapper mapper = getMapper(include, serializationConfig, mixIns);
            return mapper.writeValueAsString(object);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.Json_SerializationFailure, ex);
        }
    }

    /**
     * 将字符串反序列化为JsonNode
     *
     * @param json json字符串
     * @return com.fasterxml.jackson.databind.JsonNode
     * @author wushi
     * @date 2020/11/3 17:30
     */
    public static JsonNode parseObject(String json) {
        try {
            return parseObject(json, (DeserializationConfig) null);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.Json_DeserializationFailure, ex);
        }
    }


    /**
     * 将字符串反序列化为JsonNode
     *
     * @param json                  json字符串
     * @param deserializationConfig 自定义反序列化配置，JsonUtils.getDeserializationConfig()
     * @return com.fasterxml.jackson.databind.JsonNode
     * @author wushi
     * @date 2020/11/3 17:30
     */
    public static JsonNode parseObject(String json, DeserializationConfig deserializationConfig) {
        try {
            ObjectMapper mapper = getMapper(deserializationConfig);
            return mapper.readTree(json);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.Json_DeserializationFailure, ex);
        }
    }

    /**
     * 将字符串反序列化为Java类
     *
     * @param json  json字符串
     * @param clazz 类型
     * @return com.fasterxml.jackson.databind.JsonNode
     * @author wushi
     * @date 2020/11/3 17:30
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return parseObject(json, clazz, null);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.Json_DeserializationFailure, ex);
        }
    }

    /**
     * 将字符串反序列化为Java类
     *
     * @param json                  json字符串
     * @param clazz                 类型
     * @param deserializationConfig 自定义反序列化配置，JsonUtils.getDeserializationConfig()
     * @return com.fasterxml.jackson.databind.JsonNode
     * @author wushi
     * @date 2020/11/3 17:30
     */
    public static <T> T parseObject(String json, Class<T> clazz, DeserializationConfig deserializationConfig) {
        try {
            ObjectMapper copy = getMapper(deserializationConfig);
            return copy.readValue(json, clazz);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.Json_DeserializationFailure, ex);
        }
    }

    /**
     * 将字符串反序列化为List
     *
     * @param json   json字符串
     * @param tClass 类型
     * @return com.fasterxml.jackson.databind.JsonNode
     * @author wushi
     * @date 2020/11/3 17:30
     */
    public static <T> List<T> parseList(String json, Class<T> tClass) {
        try {
            return parseList(json, tClass, null);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.Json_DeserializationFailure, ex);
        }
    }


    /**
     * 将字符串反序列化为List
     *
     * @param json                  json字符串
     * @param tClass                类型
     * @param deserializationConfig 自定义反序列化配置，JsonUtils.getDeserializationConfig()
     * @return com.fasterxml.jackson.databind.JsonNode
     * @author wushi
     * @date 2020/11/3 17:30
     */
    public static <T> List<T> parseList(String json, Class<T> tClass, DeserializationConfig deserializationConfig) {
        try {
            ObjectMapper copy = getMapper(deserializationConfig);
            JavaType javaType = copy.getTypeFactory().constructParametricType(List.class, tClass);
            return copy.readValue(json, javaType);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.Json_DeserializationFailure, ex);
        }
    }

    /**
     * 将字符串反序列化为List
     *
     * @param json   json字符串
     * @param tClass 类型
     * @return com.fasterxml.jackson.databind.JsonNode
     * @author wushi
     * @date 2020/11/3 17:30
     */
    public static <T> T[] parseArray(String json, Class<T> tClass) {
        try {
            return parseArray(json, tClass, null);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.Json_DeserializationFailure, ex);
        }
    }


    /**
     * 将字符串反序列化为List
     *
     * @param json                  json字符串
     * @param tClass                类型
     * @param deserializationConfig 反序列化配置，JsonUtils.getDeserializationConfig()
     * @return com.fasterxml.jackson.databind.JsonNode
     * @author wushi
     * @date 2020/11/3 17:30
     */
    public static <T> T[] parseArray(String json, Class<T> tClass, DeserializationConfig deserializationConfig) {
        try {
            ObjectMapper copy = getMapper(deserializationConfig);
            JavaType javaType = copy.getTypeFactory().constructArrayType(tClass);
            return copy.readValue(json, javaType);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.Json_DeserializationFailure, ex);
        }
    }

    public static <T> T toJavaObject(JsonNode jsonNode, Class<T> tClass) throws JsonProcessingException {
        return getInstance().treeToValue(jsonNode, tClass);
    }

    /**
     * 获取ObjectMapper
     *
     * @param deserializationConfig
     * @return com.fasterxml.jackson.databind.ObjectMapper
     * @author wushi
     * @date 2020/11/5 15:30
     */
    private static ObjectMapper getMapper(DeserializationConfig deserializationConfig) {
        ObjectMapper copy = mapper;
        if (deserializationConfig != null) {
            copy = mapper.copy();
            copy.setConfig(deserializationConfig);
        }
        return copy;
    }

    private static ObjectMapper getMapper(JsonInclude.Include include, SerializationConfig serializationConfig) {
        return getMapper(include, serializationConfig, null);
    }

    private static ObjectMapper getMapper(JsonInclude.Include include, SerializationConfig serializationConfig, SimpleMixInResolver mixIns) {
        ObjectMapper copy = mapper;
        if (include != null || serializationConfig != null) {
            copy = mapper.copy();
        }
        if (serializationConfig != null) {
            copy.setConfig(serializationConfig);
        }
        if (include != null) {
            copy.setSerializationInclusion(include);
        }
        if (mixIns != null) {
            copy.setMixInResolver(mixIns);
        }
        return copy;
    }
}
