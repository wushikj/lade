package com.wushi.lade.cache.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 统一换成接口
 *
 * @author wushi
 * @date 2020/1/14 10:39
 */
public interface Cache {

    /**
     * 设置过期时间
     *
     * @param key         缓存key
     * @param expiredTime 过期时间，单位为秒
     * @return
     */
    Boolean expire(String key, int expiredTime);

    /**
     * 判断缓存Key是否存在
     *
     * @param key 缓存key
     * @return
     */
    Boolean hasKey(String key);

    /**
     * 返回所有key
     *
     * @return
     */
    List<String> keys();

    /**
     * 返回所有key（缓存key模糊匹配）
     *
     * @param pattern 缓存key模糊匹配
     * @return
     */
    List<String> keys(String pattern);

    /**
     * 查询缓存值
     *
     * @param key 缓存key
     * @return
     */
    Object get(String key);

    /**
     * 查询缓存值
     *
     * @param key   缓存key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 批量查询缓存值
     *
     * @param keys  缓存key列表
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> get(Collection<String> keys, Class<T> clazz);

    /**
     * 新增缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     * @return void
     * @author wushi
     * @date 2020/1/13 16:21
     */
    void put(String key, Object value);

    /**
     * 批量新增缓存
     *
     * @param maps 缓存key 缓存value
     */
    <T> void put(Map<String, T> maps);

    /**
     * 新增缓存（含过期时间）
     *
     * @param key         缓存key
     * @param value       缓存value
     * @param expiredTime 过期时间，单位为秒
     */
    void put(String key, Object value, int expiredTime);

    /**
     * 删除缓存（按Key）
     *
     * @param key 缓存key
     * @return void
     * @author wushi
     * @date 2020/1/13 16:22
     */
    void remove(String key);

    /**
     * 批量删除key
     *
     * @param keys
     */
    void remove(Collection<String> keys);

    /**
     * 删除缓存（key模糊匹配）
     *
     * @param pattern 缓存key模糊匹配
     * @return void
     * @author wushi
     * @date 2020/1/13 16:22
     */
    boolean removeByPattern(String pattern);

    /**
     * 删除缓存（全部）
     *
     * @return void
     * @author wushi
     * @date 2020/1/13 16:22
     */
    boolean clear();
}
