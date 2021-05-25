package com.wushi.lade.tenant.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wushi
 * @date 2020/1/13 17:06
 * @description
 */
public class DominTenantResolver implements ITenantResolver {

    @Autowired(required = false)
    ITenantValidate tenantValidate;

    @Override
    public String getTenantId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String domain = request.getServerName();
        String tenantId = "";
        String split = ".";
        if (domain.indexOf(split) > 0) {
            String[] domains = domain.split(split);
            tenantId = domains[0];
        } else {
            tenantId = domain;
        }
        if (tenantValidate != null) {
            return tenantValidate.validate(tenantId);
        } else {
            return tenantId;
        }
    }
}
