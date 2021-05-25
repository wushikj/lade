package com.wushi.lade.web;

import com.wushi.lade.LadeTestsApplication;
import com.wushi.lade.web.filter.ip.IpCache;
import com.wushi.lade.web.filter.ip.IpFilterProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LadeTestsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class IPFilterTest {

    private IpFilterProperties ipFilterProperties;
    private IpCache ipCache;

    @Autowired
    public void setIpFilterProperties(IpFilterProperties ipFilterProperties) throws Exception {
        this.ipFilterProperties = ipFilterProperties;
        ipCache = new IpCache();
        ipCache.initIp(ipFilterProperties);
    }

    @Test
    public void testIpFilter() {
        String ip = "192.168.1.1";
        switch (ipFilterProperties.getMode()) {
            case NONE:
                //不做处理
                System.out.println("==================allowed");
                break;
            case ALLOWED:
                //白名单，读取wushi.security.ip-filter.allowed配置
                testFilterByAllowedList(ipFilterProperties.getAllowed(), ip);
                break;
            case BLOCKED:
                //黑名单，读取wushi.security.ip-filter.blocked配置
                testFilterByBlockedList(ipFilterProperties.getBlocked(), ip);
                break;
        }
    }

    public void testFilterByAllowedList(String allowedRules, String requestIp) {
        if (StringUtils.isEmpty(allowedRules)) {
            //所有均不可见
            System.out.println("==================denied");
            return;
        }
        if (ipCache.isContainsIp(requestIp)) {
            System.out.println("==================allowed");
        } else {
            System.out.println("==================denied");
        }
    }

    public void testFilterByBlockedList(String blockedRules, String requestIp) {
        if (StringUtils.isEmpty(blockedRules)) {
            //所有均可见
            return;
        }
        if (ipCache.isContainsIp(requestIp)) {
            System.out.println("==================denied");
        } else {
            System.out.println("==================allowed");
        }
    }

}
