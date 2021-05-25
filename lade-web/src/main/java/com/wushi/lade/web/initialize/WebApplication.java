package com.wushi.lade.web.initialize;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.function.Consumer;

/**
 * 项目初始化方法，集成run方法
 *
 * @author wushi
 * @date 2020/1/21 8:52
 * @description
 */
public class WebApplication {

    /**
     * 项目初始化方法
     *
     * @param args
     * @author: wushi
     * @date: 2020/1/21 8:55
     * @description:
     * @return:
     */
    public static <T> ConfigurableApplicationContext init(String[] args, Class<T> primarySource) {
        return init(args, primarySource, null, null);
    }

    /**
     * 项目初始化方法
     *
     * @param args
     * @param beforeInit 调用run方法前的回调方法，可以传null，也可以传lambda表达式如：obj->{}
     * @param afterInit  调用run方法后的回调方法，可以设置ApplicationContext，可以传null，也可以传lambda表达式如：configurableApplicationContext->{}
     * @author: wushi
     * @date: 2020/1/21 8:55
     * @description:
     * @return:
     */
    public static <T> ConfigurableApplicationContext init(String[] args, Class<T> primarySource, Consumer<String[]> beforeInit, Consumer<ConfigurableApplicationContext> afterInit) {
        if (beforeInit != null) {
            beforeInit.accept(args);
        }
        ConfigurableApplicationContext applicationContext = SpringApplication.run(primarySource, args);
        if (afterInit != null) {
            afterInit.accept(applicationContext);
        }
        return applicationContext;
    }
}
