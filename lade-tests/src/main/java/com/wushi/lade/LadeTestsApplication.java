package com.wushi.lade;

import com.wushi.lade.web.initialize.WebApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * 单元测试启动项
 *
 * @author wushi
 * @date 2020/1/7 10:17
 */
@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = {"com.wushi"}, exclude = {DataSourceAutoConfiguration.class})
@MapperScan({"com.wushi.lade.tests.mapper", "com.baomidou.springboot.*.mapper"})
public class LadeTestsApplication {

    public static void main(String[] args) {
        WebApplication.init(args, LadeTestsApplication.class, obj -> {
            System.out.println("初始化前的方法");
        }, configurableApplicationContext -> {
            System.out.println("初始化后的方法");
        });
    }

}
