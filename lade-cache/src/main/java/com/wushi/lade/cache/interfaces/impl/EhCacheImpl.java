package com.wushi.lade.cache.interfaces.impl;

import com.wushi.lade.cache.interfaces.Cache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * EhCache缓存实现
 *
 * @author wushi
 * @date 2020/1/14 9:08
 */
@Service
@ConditionalOnProperty(prefix = "wushi.cache", name = "type", havingValue = "ehcache", matchIfMissing = true)
public class EhCacheImpl implements Cache {

    @Resource
    private net.sf.ehcache.CacheManager cacheManager;

    public static final String CACHE_NAME = "ec_ehcache";
    private static final Logger logger = LoggerFactory.getLogger(EhCacheImpl.class);

    /**
     * 设置过期时间
     *
     * @param key         缓存key
     * @param expiredTime 过期时间，单位为秒
     * @return
     */
    @Override
    public Boolean expire(String key, int expiredTime) {
        boolean result = false;
        net.sf.ehcache.Cache cache = cacheManager.getCache(CACHE_NAME);
        Element element = cache.get(key);
        if (element != null){
            element.setTimeToIdle(expiredTime);
            cache.put(element);
            result = true;
        }
        return result;
    }

    /**
     * 判断key是否存在
     *
     * @param key 缓存key
     * @return
     */
    @Override
    public Boolean hasKey(String key) {
        net.sf.ehcache.Cache cache = cacheManager.getCache(CACHE_NAME);
        Element element = cache.getQuiet(key);
        logger.info("element:" + element);
        if (element != null) {
            logger.info("cache.isExpired(element):" + cache.isExpired(element));
            logger.info("cacheName:" + CACHE_NAME + " key:" + key + " isExist:true");
            return true;
        } else {
            logger.info("cacheName:" + CACHE_NAME + " key:" + key + " isExist:false");
            return false;
        }
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
        List<String> result = new ArrayList<>();
        net.sf.ehcache.Cache cache = cacheManager.getCache(CACHE_NAME);
        List<String> keys = cache.getKeys();
        if (StringUtils.isEmpty(pattern)) {
            return keys;
        }
        for (Object cacheKey : keys) {
            Pattern p = Pattern.compile(pattern);
            if (p.matcher((String) cacheKey).find()) {
                result.add((String) cacheKey);
            }
        }
        return result;
    }

    @Override
    public Object get(String key) {
        return get(key, Object.class);
    }

    /**
     * 查询缓存
     *
     * @param key 缓存key
     * @return
     */
    @Override
    public <T> T get(String key, Class<T> clazz) {
        net.sf.ehcache.Cache cache = cacheManager.getCache(CACHE_NAME);
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return (T) element.getObjectValue();
    }

    /**
     * 批量查询缓存值
     *
     * @param keys  缓存keys
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> get(Collection<String> keys, Class<T> clazz) {
        List<T> resultList = new ArrayList<>();
        net.sf.ehcache.Cache cache = cacheManager.getCache(CACHE_NAME);
        Map<Object, Element> elementMap = cache.getAll(keys);
        for (Map.Entry<Object, Element> map : elementMap.entrySet()) {
            Element element = map.getValue();
            Object objectValue = null;
            if (element != null) {
                objectValue = element.getObjectValue();
            }
            resultList.add((T) objectValue);
        }
        return resultList;
    }

    /**
     * 新增缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    @Override
    public void put(String key, Object value) {
        net.sf.ehcache.Cache cache = cacheManager.getCache(CACHE_NAME);
        // key:根据此值获取缓存的value，不可重复，value值需要缓存的数据
        Element element = new Element(key, value);
        cache.put(element);
    }

    /**
     * 批量新增缓存
     *
     * @param maps 缓存key 缓存value
     * @param <T>  value泛型
     */
    @Override
    public <T> void put(Map<String, T> maps) {
        net.sf.ehcache.Cache cache = cacheManager.getCache(CACHE_NAME);
        List<Element> elementList = new ArrayList<>();
        for (Map.Entry<String, T> map : maps.entrySet()) {
            String key = map.getKey();
            T value = map.getValue();
            // key:根据此值获取缓存的value，不可重复，value值需要缓存的数据
            Element element = new Element(key, value);
            elementList.add(element);
        }
        cache.putAll(elementList);
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
        net.sf.ehcache.Cache cache = cacheManager.getCache(CACHE_NAME);
        // key:根据此值获取缓存的value，不可重复，value值需要缓存的数据
        Element element = new Element(key, value);
        element.setTimeToLive(expiredTime);
        cache.put(element);
    }

    /**
     * 按key删除缓存
     *
     * @param key 缓存key
     */
    @Override
    public void remove(String key) {
        net.sf.ehcache.Cache cache = cacheManager.getCache(CACHE_NAME);
        cache.remove(key);
    }

    /**
     * 批量删除key
     *
     * @param keys
     */
    @Override
    public void remove(Collection<String> keys) {
        net.sf.ehcache.Cache cache = cacheManager.getCache(CACHE_NAME);
        cache.remove(keys);
    }

    /**
     * 删除缓存（key模糊匹配）
     *
     * @param pattern 缓存key模糊匹配
     */
    @Override
    public boolean removeByPattern(String pattern) {
        boolean result = false;
        net.sf.ehcache.Cache cache = cacheManager.getCache(CACHE_NAME);
        List keys = cache.getKeys();
        List<String> deleteKeys = new ArrayList<>();
        for (Object cacheKey : keys) {
            Pattern p = Pattern.compile(pattern);
            if (p.matcher((String) cacheKey).find()) {
                deleteKeys.add((String) cacheKey);
            }
        }
        if (deleteKeys.size() > 0) {
            cache.removeAll(deleteKeys);
            result = true;
        }
        return result;
    }

    /**
     * 清除所有的缓存
     */
    @Override
    public boolean clear() {
        net.sf.ehcache.Cache cache = cacheManager.getCache(CACHE_NAME);
        cache.removeAll();
        return true;
    }
}
