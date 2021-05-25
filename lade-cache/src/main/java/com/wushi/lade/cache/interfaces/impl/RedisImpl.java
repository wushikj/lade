package com.wushi.lade.cache.interfaces.impl;

import com.wushi.lade.cache.interfaces.Cache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存实现
 *
 * @author wushi
 * @date 2020/1/14 9:08
 */
@Service
@ConditionalOnProperty(prefix = "wushi.cache", name = "type", havingValue = "redis")
public class RedisImpl implements Cache {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置过期时间
     *
     * @param key         缓存key
     * @param expiredTime 过期时间，单位为秒
     * @return
     */
    @Override
    public Boolean expire(String key, int expiredTime) {
        return redisTemplate.expire(key, expiredTime, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 缓存key
     * @return
     */
    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取所有的key
     *
     * @return
     */
    @Override
    public List<String> keys() {
        return keys(null);
    }

    /**
     * 获取所有的key，可根据正则表达式过滤
     *
     * @param pattern 正则表达式
     * @return
     */
    @Override
    public List<String> keys(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = "*";
        }
        return new ArrayList<>(redisTemplate.keys(pattern));
    }

    /**
     * 获取缓存（按key）
     *
     * @param key 缓存key
     * @return
     */
    @Override
    public Object get(String key) {
        return get(key, Object.class);
    }

    /**
     * 获取缓存（按key）
     *
     * @param key 缓存key
     * @return
     */
    @Override
    public <T> T get(String key, Class<T> clazz) {
        return key == null ? null : (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 批量获取缓存（按keys）
     *
     * @param keys  缓存key列表
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> get(Collection<String> keys, Class<T> clazz) {
        return (List<T>) redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 新增缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    @Override
    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 批量新增缓存
     *
     * @param maps 缓存key 缓存value
     * @param <T>  value泛型
     */
    @Override
    public <T> void put(Map<String, T> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 新增缓存（含过期时间）
     *
     * @param key         缓存key
     * @param value       缓存value
     * @param expiredTime 过期时间，单位为秒
     */
    @Override
    public void put(String key, Object value, int expiredTime) {
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.SECONDS);
    }

    /**
     * 删除缓存（按缓存key）
     *
     * @param key 缓存key
     */
    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys
     */
    @Override
    public void remove(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 删除缓存（key模糊匹配）
     *
     * @param pattern 缓存key模糊匹配
     */
    @Override
    public boolean removeByPattern(String pattern) {
        boolean result = false;
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys != null && keys.size() > 0) {
            redisTemplate.delete(keys);
            result = true;
        }
        return result;
    }

    /**
     * 清除所有的缓存
     */
    @Override
    public boolean clear() {
        boolean result = false;
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null && keys.size() > 0) {
            redisTemplate.delete(keys);
            result = true;
        }
        return result;
    }
}
