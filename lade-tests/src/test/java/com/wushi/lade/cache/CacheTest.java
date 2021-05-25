package com.wushi.lade.cache;

import com.wushi.lade.LadeTestsApplication;
import com.wushi.lade.cache.interfaces.Cache;
import com.wushi.lade.cache.utils.RedisUtils;
import com.wushi.lade.tests.entity.SysUser;
import com.wushi.lade.tests.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

/**
 * 统一缓存接口，单元测试
 *
 * @author wushi
 * @date 2020/1/15 16:03
 */
@SpringBootTest(classes = LadeTestsApplication.class)
public class CacheTest {

    @Resource
    private Cache cache;

    /**
     * 判断key是否存在
     */
    @Test
    public void hasKey() {
        Boolean aBoolean = cache.hasKey("test2");
        System.out.println("hasKey结果：" + aBoolean);
    }

    /**
     * 返回缓存key列表，全量
     */
    @Test
    public void keys() {
        List<String> keys = cache.keys();
        System.out.println("keys结果：" + keys);
    }

    /**
     * 返回所有的key，按表达式过滤
     */
    @Test
    public void keysPattern() {
        List<String> keys = cache.keys("test*");
        System.out.println("keysPattern结果：" + keys);
    }

    /**
     * 查询缓存
     */
    @Test
    public void get() {
        System.out.println("=======【Get】缓存接口测试开始=======");
        put();
        hasKey();
        Object test = cache.get("test2");
        System.out.println("结果：" + test);
        System.out.println("=======【Get】缓存接口测试结束=======");
    }

    /**
     * 批量查询缓存值
     */
    @Test
    public void multiGet() {
        List<String> keys = new ArrayList<>();
        keys.add("test2");
        keys.add("test");
        List<Object> resultList = cache.get(keys, Object.class);
        System.out.println("multiGet结果：" + resultList);
    }

    /**
     * 新增缓存
     */
    @Test
    public void put() {
        System.out.println("=======【Put】缓存接口测试开始=======");
        SysUser sysUser = new SysUser();
        sysUser.setRealName("lhx2");
        sysUser.setRemark("test2");
        cache.put("test2", sysUser);
        System.out.println("=======【Put】缓存接口测试结束=======");
    }

    /**
     * 新增缓存含时间
     */
    @Test
    public void putWithTime() {
        System.out.println("=======【Put含过期时间】缓存接口测试开始=======");
        cache.put("test", UUID.randomUUID(), 60);
        System.out.println("=======【Put含过期时间】缓存接口测试结束=======");
    }

    /**
     * 删除缓存，按缓存key
     */
    @Test
    public void remove() {
        System.out.println("=======【Remove】缓存接口测试开始=======");
        cache.remove("test2");
        System.out.println("=======【Remove】缓存接口测试结束=======");
    }

    /**
     * 清空缓存
     */
    @Test
    public void clear() {
        System.out.println("=======【Clear】缓存接口测试开始=======");
        cache.clear();
        System.out.println("=======【Clear】缓存接口测试结束=======");
    }

    @Test
    public void mapTest() {
        Map<String, String> map = new HashMap<>();
        map.put("11", "11");
        map.put("22", "22");
        cache.put("111", map);
    }

    @Test
    public void redisTest() {
        Student student = new Student();
        student.setStudentName("123");
        student.setAge(20);
        student.setSex(1);
        student.setStudentId(1L);
        RedisUtils.set("student", student);
    }
}
