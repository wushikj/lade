package com.wushi.lade.tenant;

import com.wushi.lade.tenant.resolver.ITenantResolver;
import com.wushi.lade.tests.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author wushi
 * @date 2020/1/20 16:59
 * @description
 */
@Component
@Primary
public class TenantResolverImpl implements ITenantResolver {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public String getTenantId() {
        return "1";
    }
}
