package com.wushi.lade.tenant;

import com.wushi.lade.tenant.resolver.ITenantValidate;
import com.wushi.lade.tests.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author wushi
 * @date 2020/1/20 10:40
 * @description
 */
@Component
@Primary
public class TenantValidateImpl implements ITenantValidate {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public String validate(String tenantId) {
        return tenantId;
    }
}
