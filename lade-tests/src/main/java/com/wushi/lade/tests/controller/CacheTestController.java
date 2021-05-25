package com.wushi.lade.tests.controller;

import com.wushi.lade.cache.interfaces.Cache;
import com.wushi.lade.tests.entity.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author wushi
 * @date 2020/3/30 11:06
 */
@Controller
@RequestMapping("/cache")
public class CacheTestController {
    @Resource
    private Cache cache;

    @RequestMapping(value = "/expire")
    @ResponseBody
    public Boolean expire() {
        Boolean aBoolean = cache.expire("test", 30);
        System.out.println("expire结果：" + aBoolean);
        return aBoolean;
    }

    @RequestMapping(value = "/hasKey")
    @ResponseBody
    public Boolean hasKey() {
        Boolean aBoolean = cache.hasKey("test2");
        System.out.println("hasKey结果：" + aBoolean);
        return aBoolean;
    }

    @RequestMapping(value = "/keys")
    @ResponseBody
    public List<String> keys() {
        List<String> keys = cache.keys();
        System.out.println("keys结果：" + keys);
        return keys;
    }

    @RequestMapping(value = "/keysPattern")
    @ResponseBody
    public List<String> keysPattern() {
        List<String> keys = cache.keys("test*");
        System.out.println("keysPattern结果：" + keys);
        return keys;
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public Object get() {
        System.out.println("=======【Get】缓存接口测试开始=======");
        Object test = cache.get("test");
        System.out.println("结果：" + test);
        System.out.println("=======【Get】缓存接口测试结束=======");
        return test;
    }

    @RequestMapping(value = "/multiGet")
    @ResponseBody
    public List<Object> multiGet() {
        List<String> keys = new ArrayList<>();
        keys.add("test2");
        keys.add("test");
        List<Object> resultList = cache.get(keys, Object.class);
        System.out.println("multiGet结果：" + resultList);
        return resultList;
    }

    @RequestMapping(value = "/put")
    @ResponseBody
    public void put() {
        System.out.println("=======【Put】缓存接口测试开始=======");
        SysUser sysUser = new SysUser();
        sysUser.setRealName("lhx2");
        sysUser.setRemark("test2");
        cache.put("test2", sysUser);
        System.out.println("=======【Put】缓存接口测试结束=======");
    }


    @RequestMapping(value = "/putWithTime")
    @ResponseBody
    public void putWithTime() {
        System.out.println("=======【Put含过期时间】缓存接口测试开始=======");
        cache.put("test", UUID.randomUUID(), 60);
        System.out.println("=======【Put含过期时间】缓存接口测试结束=======");
    }

    @RequestMapping(value = "/multiPut")
    @ResponseBody
    public void multiPut() {
        System.out.println("=======【Put】批量缓存接口测试开始=======");
        SysUser sysUser1 = new SysUser();
        sysUser1.setRealName("lhx3");
        sysUser1.setRemark("test3");
        SysUser sysUser2 = new SysUser();
        sysUser2.setRealName("lhx4");
        sysUser2.setRemark("test4");
        Map<String, Object> map = new HashMap<>();
        map.put("test", sysUser1);
        map.put("test2", sysUser2);
        cache.put(map);
        System.out.println("=======【Put】批量接口测试结束=======");
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public void remove() {
        System.out.println("=======【Remove】缓存接口测试开始=======");
        cache.remove("test2");
        System.out.println("=======【Remove】缓存接口测试结束=======");
    }
}
