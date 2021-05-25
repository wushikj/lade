package com.wushi.lade.cache.utils;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.DefaultTuple;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
@ConditionalOnProperty(prefix = "wushi.cache", name = "type", havingValue = "redis")
public class RedisUtils implements ApplicationContextAware {

    private static RedisTemplate redisTemplate = null;

    public static final GenericJackson2JsonRedisSerializer JACKSON_SERIALIZER = new GenericJackson2JsonRedisSerializer();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedisUtils.redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");
    }

    /**
     * 删除key
     *
     * @param key
     */
    public static void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys
     */
    public static void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 序列化key
     *
     * @param key
     * @return
     */
    public static byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    public static Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    public static Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param date
     * @return
     */
    public static Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 查找匹配的key
     *
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中
     *
     * @param key
     * @param dbIndex
     * @return
     */
    public static Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 移除 key 的过期时间，key 将持久保持
     *
     * @param key
     * @return
     */
    public static Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key
     * @param unit
     * @return
     */
    public static Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key
     * @return
     */
    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 从当前数据库中随机返回一个 key
     *
     * @return
     */
    public static String randomKey() {
        return (String) redisTemplate.randomKey();
    }

    /**
     * 修改 key 的名称
     *
     * @param oldKey
     * @param newKey
     */
    public static void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
     *
     * @param oldKey
     * @param newKey
     * @return
     */
    public static Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 返回 key 所储存的值的类型
     *
     * @param key
     * @return
     */
    public static DataType type(String key) {
        return redisTemplate.type(key);
    }

    /** -------------------string相关操作--------------------- */

    /**
     * 设置指定 key 的值
     *
     * @param key
     * @param value
     */
    public static <T> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置指定 key 的值
     *
     * @param key
     * @param value
     */
    public static <T> void set(String key, T value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取指定 key 的值
     *
     * @param key
     * @return
     */
    public static <T> T get(String key, Class<T> clz) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 返回 key 中字符串值的子字符
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static <T> T getRange(String key, long start, long end, Class<T> clz) {
        return (T) redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     *
     * @param key
     * @param value
     * @return
     */
    public static <T> T getAndSet(String key, T value, Class<T> clz) {
        return (T) redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
     *
     * @param key
     * @param offset
     * @return
     */
    public static Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 批量获取
     *
     * @param keys
     * @return
     */
    public static <T> List<T> multiGet(Collection<String> keys, Class<T> clz) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
     *
     * @param key
     * @param offset 位置
     * @param value  值,true为1, false为0
     * @return
     */
    public static boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     *
     * @param key
     * @param value
     * @param timeout 过期时间
     * @param unit    时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
     *                秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
     */
    public static <T> void setEx(String key, T value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key
     * @param value
     * @return 之前已经存在返回false, 不存在返回true
     */
    public static <T> boolean setIfAbsent(String key, T value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     *
     * redisUtil.setIfAbsent 新加的带有超时的setIfAbsent 脚本
     */
//    private static String newSetIfAbsentScriptStr =
//            " if 1 == redis.call('setnx',KEYS[1],ARGV[1]) then" +
//            " redis.call('expire',KEYS[1],ARGV[2])" +
//            " return '1';" +
//            " else" +
//            " return '0';" +
//            " end;";
//
//    public static RedisScript<String> newSetIfAbsentScript = new DefaultRedisScript<String>(newSetIfAbsentScriptStr,String.class );
//    public static <T>  boolean setIfAbsent(String key, T value, Long seconds){
//
//        List<String> keys = new ArrayList<String>();
//        keys.add(key);
//        Object[] args = {JedisCacheManager.FASTJSON_SERIALIZER.serialize(value), seconds.toString()};
//        return "1".equals(redisTemplate.execute(newSetIfAbsentScript,keys, args));
//    }

    /**
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
     *
     * @param key
     * @param value
     * @param offset 从指定位置开始覆写
     */
    public static <T> void setRange(String key, T value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获取字符串的长度
     *
     * @param key
     * @return
     */
    public static Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 批量添加
     *
     * @param maps
     */
    public static <T> void multiSet(Map<String, T> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     *
     * @param maps
     * @return 之前已经存在返回false, 不存在返回true
     */
    public static <T> boolean multiSetIfAbsent(Map<String, T> maps) {
        return redisTemplate.opsForValue().multiSetIfAbsent(maps);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key
     * @param increment
     * @return
     */
    public static Long incrBy(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * @param key
     * @param increment
     * @return
     */
    public static Double incrByFloat(String key, double increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 追加到末尾
     *
     * @param key
     * @param value
     * @return
     */
    public static Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    /** -------------------hash相关操作------------------------- */

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key
     * @param field
     * @return
     */
    public static <T> T hGet(String key, String field, Class<T> clz) {
        return (T) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key
     * @return
     */
    public static <T> Map<String, T> hGetAll(String key, Class<T> clz) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key
     * @param fields
     * @return
     */
    public static <T> List<T> hMultiGet(String key, Collection<Object> fields, Class<T> clz) {
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    public static <T> void hPut(String key, String hashKey, T value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public static <T> void hPutAll(String key, Map<Object, T> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    public static <T> void hPutAllKey(String key, Map<String, T> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * 仅当hashKey不存在时才设置
     *
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public static <T> Boolean hPutIfAbsent(String key, String hashKey, T value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key
     * @param fields
     * @return
     */
    public static Long hDelete(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key
     * @param field
     * @return
     */
    public static boolean hExists(String key, Object field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key
     * @param field
     * @param increment
     * @return
     */
    public static Long hIncrBy(String key, Object field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key
     * @param field
     * @param delta
     * @return
     */
    public static Double hIncrByFloat(String key, Object field, double delta) {
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key
     * @return
     */
    public static Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key
     * @return
     */
    public static Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key
     * @return
     */
    public static <T> List<T> hValues(String key, Class<T> clz) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key
     * @param options
     * @return
     */
    public static <T> Cursor<Map.Entry<Object, T>> hScan(String key, ScanOptions options, Class<T> clz) {
        return redisTemplate.opsForHash().scan(key, options);
    }

    /** ------------------------list相关操作---------------------------- */

    /**
     * 通过索引获取列表中的元素
     *
     * @param key
     * @param index
     * @return
     */
    public static <T> T lIndex(String key, long index, Class<T> clz) {
        return (T) redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param key
     * @param start 开始位置, 0是开始位置
     * @param end   结束位置, -1返回所有
     * @return
     */
    public static <T> List<T> lRange(String key, long start, long end, Class<T> clz) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 存储在list头部
     *
     * @param key
     * @param value
     * @return
     */
    public static <T> Long lLeftPush(String key, T value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static <T> Long lLeftPushAll(String key, T... value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static <T> Long lLeftPushAll(String key, Collection<T> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当list存在的时候才加入
     *
     * @param key
     * @param value
     * @return
     */
    public static <T> Long lLeftPushIfPresent(String key, T value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 如果pivot存在,再pivot前面添加
     *
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    public static <T> Long lLeftPush(String key, T pivot, T value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static <T> Long lRightPush(String key, T value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static <T> Long lRightPushAll(String key, T... value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static <T> Long lRightPushAll(String key, Collection<T> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 为已存在的列表添加值
     *
     * @param key
     * @param value
     * @return
     */
    public static <T> Long lRightPushIfPresent(String key, T value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 在pivot元素的右边添加值
     *
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    public static <T> Long lRightPush(String key, T pivot, T value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param key
     * @param index 位置
     * @param value
     */
    public static <T> void lSet(String key, long index, T value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @param key
     * @return 删除的元素
     */
    public static <T> T lLeftPop(String key, Class<T> clz) {
        return (T) redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return
     */
    public static <T> T lBLeftPop(String key, long timeout, TimeUnit unit, Class<T> clz) {
        return (T) redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key
     * @return 删除的元素
     */
    public static <T> T lRightPop(String key, Class<T> clz) {
        return (T) redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return
     */
    public static <T> T lBRightPop(String key, long timeout, TimeUnit unit, Class<T> clz) {
        return (T) redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     *
     * @param sourceKey
     * @param destinationKey
     * @return
     */
    public static <T> T lRightPopAndLeftPush(String sourceKey, String destinationKey, Class<T> clz) {
        return (T) redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
                destinationKey);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey
     * @param destinationKey
     * @param timeout
     * @param unit
     * @return
     */
    public static <T> T lBRightPopAndLeftPush(String sourceKey, String destinationKey,
                                              long timeout, TimeUnit unit, Class<T> clz) {
        return (T) redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
                destinationKey, timeout, unit);
    }

    /**
     * 删除集合中值等于value得元素
     *
     * @param key
     * @param index index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *              index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value
     * @return
     */
    public static <T> Long lRemove(String key, long index, T value) {
        return redisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * 裁剪list
     *
     * @param key
     * @param start
     * @param end
     */
    public static void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表长度
     *
     * @param key
     * @return
     */
    public static Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /** --------------------set相关操作-------------------------- */

    /**
     * set添加元素
     *
     * @param key
     * @param values
     * @return
     */
    public static <T> Long sAdd(String key, T... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * set移除元素
     *
     * @param key
     * @param values
     * @return
     */
    public static <T> Long sRemove(String key, T... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key
     * @return
     */
    public static <T> T sPop(String key, Class<T> clazz) {
        return (T) redisTemplate.opsForSet().pop(key);
    }

    /**
     * 将元素value从一个集合移到另一个集合
     *
     * @param key
     * @param value
     * @param destKey
     * @return
     */
    public static <T> Boolean sMove(String key, T value, String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 获取集合的大小
     *
     * @param key
     * @return
     */
    public static Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断集合是否包含value
     *
     * @param key
     * @param value
     * @return
     */
    public static <T> Boolean sIsMember(String key, T value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取两个集合的交集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public static Set<String> sIntersect(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的交集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public static Set<String> sIntersect(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的交集存储到destKey集合中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * key集合与多个集合的交集存储到destKey集合中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public static Long sIntersectAndStore(String key, Collection<String> otherKeys,
                                          String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 获取两个集合的并集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public static Set<String> sUnion(String key, String otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * 获取key集合与多个集合的并集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public static Set<String> sUnion(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的并集存储到destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long sUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的并集存储到destKey中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public static Long sUnionAndStore(String key, Collection<String> otherKeys,
                                      String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的差集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public static Set<String> sDifference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的差集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public static Set<String> sDifference(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的差集存储到destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long sDifference(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey,
                destKey);
    }

    /**
     * key集合与多个集合的差集存储到destKey中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public static Long sDifference(String key, Collection<String> otherKeys,
                                   String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 获取集合所有元素
     *
     * @param key
     * @return
     */
    public static <T> Set<T> setMembers(String key, Class<T> clz) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key
     * @return
     */
    public static <T> T sRandomMember(String key, Class<T> clz) {
        return (T) redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取集合中count个元素
     *
     * @param key
     * @param count
     * @return
     */
    public static <T> List<T> sRandomMembers(String key, long count, Class<T> clz) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     *
     * @param key
     * @param count
     * @return
     */
    public static <T> Set<T> sDistinctRandomMembers(String key, long count, Class<T> clz) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * @param key
     * @param options
     * @return
     */
    public static <T> Cursor<T> sScan(String key, ScanOptions options, Class<T> clz) {
        return redisTemplate.opsForSet().scan(key, options);
    }

    /**------------------zSet相关操作--------------------------------*/

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public static <T> Boolean zAdd(String key, T value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * @param key
     * @param values
     * @return
     */
    public static <T> Long zAdd(String key, Set<T> values) {
        return redisTemplate.opsForZSet().add(key, values);
    }

    /**
     * @param key
     * @param values
     * @return
     */
    public static <T> Long zRemove(String key, T... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key
     * @param value
     * @param delta
     * @return
     */
    public static <T> Double zIncrementScore(String key, T value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     *
     * @param key
     * @param value
     * @return 0表示第一位
     */
    public static <T> Long zRank(String key, T value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     *
     * @param key
     * @param value
     * @return
     */
    public static <T> Long zReverseRank(String key, T value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param key
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return
     */
    public static <T> Set<T> zRange(String key, long start, long end, Class<T> clz) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 获取集合元素, 并且把score值也获取
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static <T> Set<ZSetOperations.TypedTuple<T>> zRangeWithScores(String key, long start,
                                                                         long end, Class<T> clz) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static <T> Set<T> zRangeByScore(String key, double min, double max, Class<T> clz) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static <T> Set<ZSetOperations.TypedTuple<T>> zRangeByScoreWithScores(String key,
                                                                                double min, double max, Class<T> clz) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     */
    public static <T> Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key,
                                                                                     double min, double max, long start, long end, Class<T> clz) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max,
                start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static <T> Set<T> zReverseRange(String key, long start, long end, Class<T> clz) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static <T> Set<ZSetOperations.TypedTuple<T>> zReverseRangeWithScores(String key,
                                                                                long start, long end, Class<T> clz) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start,
                end);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static <T> Set<T> zReverseRangeByScore(String key, double min,
                                                  double max, Class<T> clz) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static <T> Set<ZSetOperations.TypedTuple<T>> zReverseRangeByScoreWithScores(
            String key, double min, double max, Class<T> clz) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key,
                min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     */
    public static <T> Set<T> zReverseRangeByScore(String key, double min,
                                                  double max, long start, long end, Class<T> clz) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max,
                start, end);
    }

    /**
     * 根据score值获取集合元素数量
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取集合大小
     *
     * @param key
     * @return
     */
    public static Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取集合大小
     *
     * @param key
     * @return
     */
    public static Long zZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取集合中value元素的score值
     *
     * @param key
     * @param value
     * @return
     */
    public static <T> Double zScore(String key, T value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Long zRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的score值的范围来移除成员
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long zUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * @param key
     * @param aggregate
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long zIntersectAndStore(String key, RedisZSetCommands.Aggregate aggregate, String otherKey,
                                          String destKey) {

        return (Long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            byte[][] rawKeys = rawKeys(key, Collections.singleton(otherKey));
            byte[] rawDestKey = destKey.getBytes();
            connection.del(rawDestKey);
            return connection.zInterStore(rawDestKey, aggregate, new int[]{}, rawKeys);
        }, true);
    }

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key
     * @param values
     * @return
     */
    public static <T> Long zAddCover(String key, Set<ZSetOperations.TypedTuple<T>> values) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            byte[] rawKey = key.getBytes();
            Set<RedisZSetCommands.Tuple> rawValues = rawTupleValues(values);
            connection.del(rawKey);
            return connection.zAdd(rawKey, rawValues);
        }, true);
    }

    private static <T> Set<RedisZSetCommands.Tuple> rawTupleValues(Set<ZSetOperations.TypedTuple<T>> values) {
        if (values == null) {
            return null;
        }
        Set<RedisZSetCommands.Tuple> rawTuples = new LinkedHashSet<>(values.size());
        for (ZSetOperations.TypedTuple<T> value : values) {
            byte[] rawValue;
            if (JACKSON_SERIALIZER == null && value.getValue() instanceof byte[]) {
                rawValue = (byte[]) value.getValue();
            } else {
                rawValue = JACKSON_SERIALIZER.serialize(value.getValue());
            }
            rawTuples.add(new DefaultTuple(rawValue, value.getScore()));
        }
        return rawTuples;
    }

    /**
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public static Long zUnionAndStore(String key, Collection<String> otherKeys,
                                      String destKey) {
        return redisTemplate.opsForZSet()
                .unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 交集
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long zIntersectAndStore(String key, String otherKey,
                                          String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * 交集
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public static Long zIntersectAndStore(String key, Collection<String> otherKeys,
                                          String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys,
                destKey);
    }

    /**
     * @param key
     * @param options
     * @return
     */
    public static <T> Cursor<ZSetOperations.TypedTuple<T>> zScan(String key, ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key, options);
    }

    /**
     * 添加用户经纬度
     * geoadd cityGeo 116.405285 39.904989 "北京"
     *
     * @param key
     * @param lng 经度
     * @param lat 纬度
     * @param m
     * @return
     */
    public static Long geoAdd(Object key, double lng, double lat, Object m) {
        Point point = new Point(lng, lat);
        Long num = redisTemplate.opsForGeo().add(key, point, m);
        return num;
    }

    /**
     * 批量添加用户经纬度
     *
     * @param key
     * @param map
     * @return
     */
    public static Long geoAddBatch(Object key, Map<Object, Point> map) {
        Long num = redisTemplate.opsForGeo().add(key, map);
        return num;
    }

    /**
     * 查找指定key的经纬度信息，可以指定多个key，批量返回
     * redis命令：geopos cityGeo 北京
     *
     * @param key
     * @param m
     */
    public static List<Point> geoGet(Object key, Object... m) {
        List<Point> points = redisTemplate.opsForGeo().position(key, m);
        return points;
    }

    /**
     * 返回两个地方的距离，可以指定单位，比如米m，千米km，英里mi，英尺ft
     * redis命令：geodist cityGeo 北京 上海
     *
     * @param key
     * @param m1     位置1
     * @param m2     位置2
     * @param metric Metrics.MILES 单位
     * @return
     */
    public static Distance geoDistance(Object key, Object m1, Object m2, Metric metric) {
        Distance distance = redisTemplate.opsForGeo().distance(key, m1, m2, metric);
        return distance;
    }

    /**
     * 根据给定的经纬度，返回半径不超过指定距离的元素,时间复杂度为O(N+log(M))，N为指定半径范围内的元素个数，M为要返回的个数
     * redis命令：georadius cityGeo 116.405285 39.904989 100 km WITHDIST WITHCOORD ASC COUNT 5
     *
     * @param key
     * @param lng      经度
     * @param lat      纬度
     * @param distance 范围半径
     * @param metric   单位 km m
     * @param limit    取条数
     * @param sort     排序 asc desc
     */
    public static List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> geoNearByXY(Object key, double lng, double lat,
                                                                                    double distance, String metric, long limit, String sort) {

        Circle circle = new Circle(new Point(lng, lat), new Distance(distance, getMetric(metric)));
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().includeCoordinates().limit(limit);
        switch (sort.toLowerCase()) {
            case "desc":
                args.sortDescending();
                break;
            default:
                args.sortAscending();
                break;
        }

        GeoResults<RedisGeoCommands.GeoLocation<Object>> results = redisTemplate.opsForGeo().radius(key, circle, args);
        List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> content = results.getContent();
        if (content != null) {
            return content.subList(0, content.size() > limit ? (int) limit : content.size());
        }
        return null;
    }

    /**
     * 根据指定的地点查询半径在指定范围内的位置,时间复杂度为O(log(N)+M)，N为指定半径范围内的元素个数，M为要返回的个数
     * redis命令：georadiusbymember cityGeo 北京 100 km WITHDIST WITHCOORD ASC COUNT 5
     *
     * @param key
     * @param m
     * @param dist   范围半径
     * @param metric 单位 km m
     * @param limit  取条数
     * @param sort   排序 asc desc
     * @return
     */
    public static List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> geoNearByPlace(Object key, Object m, double dist, String metric, long limit, String sort) {

        Distance distance = new Distance(dist, getMetric(metric));
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().includeCoordinates().sortAscending().limit(limit);
        switch (sort.toLowerCase()) {
            case "desc":
                args.sortDescending();
                break;
            default:
                args.sortAscending();
                break;
        }
        GeoResults<RedisGeoCommands.GeoLocation<Object>> results = redisTemplate.opsForGeo().radius(key, m, distance, args);
        List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> content = results.getContent();
        if (content != null) {
            return content.subList(0, content.size() > limit ? (int) limit : content.size());
        }
        return null;
    }

    /**
     * 返回的是geohash值,查找一个位置的时间复杂度为O(log(N))
     * redis命令：geohash cityGeo 北京
     *
     * @param key
     * @param mks
     * @return
     */
    public static List geoHash(Object key, Object... mks) {
        List<String> results = redisTemplate.opsForGeo().hash(key, mks);
        return results;
    }

    private static Metric getMetric(String metric) {
        Metric metrics = Metrics.NEUTRAL;
        switch (metric.toLowerCase()) {
            case "km":
                metrics = Metrics.KILOMETERS;
                break;
            case "m":
                metrics = Metrics.MILES;
                break;
            default:
                break;
        }
        return metrics;
    }

    private static byte[][] rawKeys(String key, Collection<String> keys) {

        byte[][] rawKeys = new byte[keys.size() + (key != null ? 1 : 0)][];
        int i = 0;
        if (key != null) {
            rawKeys[i++] = key.getBytes();
        }
        for (String k : keys) {
            rawKeys[i++] = k.getBytes();
        }
        return rawKeys;
    }


}
