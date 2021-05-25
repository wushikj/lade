package com.wushi.lade.tests.service.sqlserver.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushi.lade.tests.entity.SysUserAccount;
import com.wushi.lade.tests.mapper.SysUserAccountMapper;
import com.wushi.lade.tests.service.ISysUserAccountService;
import org.springframework.stereotype.Service;

/**
 * SysUserAccount实现类
 *
 * @author wushi
 * @date 2020-01-07 14:20
 */
@Service
@DS("sqlserver")
public class SysUserAccountBySqlServerServiceImpl extends ServiceImpl<SysUserAccountMapper, SysUserAccount> implements ISysUserAccountService {

}
