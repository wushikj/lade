package com.wushi.lade.common.utils;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.CaseFormat;

import java.util.*;

/**
 * @author wushi
 * @date 2020/2/27 10:30
 * @description
 */
public class MapKeyNamingUtils {

    /**
     * 将JSONObject对象转为小写驼峰
     *
     * @param map 转换对象
     * @author: wushi
     * @date: 2020/2/27 13:27
     * @description:
     * @return:
     */
    public static Map<String, Object> convertMap(Map<String, Object> map) {
        return convertMap(map, PropertyNamingStrategy.LOWER_CAMEL_CASE);
    }

    /**
     * 将JSONArray转为小写驼峰
     *
     * @param array 转换对象
     * @author: wushi
     * @date: 2020/2/27 13:28
     * @description:
     * @return:
     */
    public static List<Map<String, Object>> convertMapList(List<Map<String, Object>> array) {
        return convertMapList(array, PropertyNamingStrategy.LOWER_CAMEL_CASE);
    }

    /**
     * 将JSONObject对象转小写驼峰或大写驼峰
     *
     * @param map                    转换对象
     * @param propertyNamingStrategy 转换策略
     * @author: wushi
     * @date: 2020/2/27 13:29
     * @description:
     * @return:
     */
    public static Map<String, Object> convertMap(Map<String, Object> map, PropertyNamingStrategy propertyNamingStrategy) {
        Map<String, String> keyMap = new HashMap<>();
        return convertMap(map, propertyNamingStrategy, keyMap);
    }

    /**
     * 将JSONObject对象转小写驼峰或大写驼峰
     *
     * @param map                    转换对象
     * @param propertyNamingStrategy 转换策略
     * @param keyMap                 用于缓存key和转换后的key
     * @author: wushi
     * @date: 2020/2/27 13:29
     * @description:
     * @return:
     */
    private static Map<String, Object> convertMap(Map<String, Object> map, PropertyNamingStrategy propertyNamingStrategy, Map<String, String> keyMap) {
        if (map == null) {
            return null;
        }
        Map<String, Object> newMap = new HashMap<>();
        Set<String> set = map.keySet();
        for (String key : set) {
            Object value = map.get(key);
            //这个方法自己写的改成驼峰，也可以改成大写小写
            String newKey = "";
            if (keyMap.containsKey(key)) {
                newKey = keyMap.get(key);
            } else {
                newKey = convertName(key, propertyNamingStrategy);
                keyMap.put(key, newKey);
            }
            if (value == null) {
                newMap.put(newKey, null);
                continue;
            }
            if (value.getClass().isAssignableFrom(List.class)) {
                //数组
                value = convertMapList((List<Map<String, Object>>) value, propertyNamingStrategy);
            } else if (value instanceof Map) {
                //对象
                value = convertMap((Map<String, Object>) value, propertyNamingStrategy, keyMap);
            }
            newMap.put(newKey, value);
        }
        return newMap;
    }

    /**
     * 将JSONArray对象转小写驼峰或大写驼峰
     *
     * @param array                  转换对象
     * @param propertyNamingStrategy 转换策略
     * @author: wushi
     * @date: 2020/2/27 13:29
     * @description:
     * @return:
     */
    public static List<Map<String, Object>> convertMapList(List<Map<String, Object>> array, PropertyNamingStrategy propertyNamingStrategy) {
        if (array == null) {
            return null;
        }
        //不需要转
        if (propertyNamingStrategy == null) {
            return array;
        }
        List<Map<String, Object>> newMapList = new ArrayList<>();
        int arraySize = array.size();
        Map<String, String> keyMap = new HashMap<>();
        for (int i = 0; i < arraySize; i++) {
            Object value = array.get(i);
            //对象
            Map<String, Object> newValue = convertMap((Map<String, Object>) array.get(i), propertyNamingStrategy, keyMap);
            newMapList.add(newValue);
        }
        return newMapList;
    }

    /**
     * 将字符串转为小写驼峰
     *
     * @param key
     * @author: wushi
     * @date: 2020/2/27 13:24
     * @description:
     * @return:
     */
    public static String convertName(String key) {
        return convertName(key, PropertyNamingStrategy.LOWER_CAMEL_CASE);
    }


    /**
     * 将字符串转为小写驼峰或大写驼峰
     *
     * @param key
     * @param propertyNamingStrategy 转换策略
     * @author: wushi
     * @date: 2020/2/27 13:32
     * @description:
     * @return:
     */
    public static String convertName(String key, PropertyNamingStrategy propertyNamingStrategy) {
        if (propertyNamingStrategy == PropertyNamingStrategy.LOWER_CAMEL_CASE) {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
        } else if (propertyNamingStrategy == PropertyNamingStrategy.UPPER_CAMEL_CASE) {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, key);
        } else {
            return key;
        }
    }
}

