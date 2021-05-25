package com.wushi.lade.tests.service.oracle.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushi.lade.tests.entity.SysUser;
import com.wushi.lade.tests.mapper.SysUserMapper;
import com.wushi.lade.tests.service.ISysUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * SysUser服务实现类
 *
 * @author wushi
 * @date 2020-01-07 14:20
 */
@Service
@DS("oracle")
public class SysUserByOracleServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    /**
     * Insert单条记录，返回影响条数
     *
     * @param sysUser
     * @return int
     * @author wushi
     * @date 2020/1/8 14:22
     */
    @Override
    public int insertReturnAffectRow(SysUser sysUser) {
        return baseMapper.insert(sysUser);
    }

    /**
     * left join查询
     *
     * @param userId 用户ID
     * @return com.wushi.lade.tests.entity.UserVO
     * @author wushi
     * @date 2020/1/9 10:01
     */
    @Override
    public List<Map<String, Object>> selectWithAccount(String userId) {
        return baseMapper.selectWithAccount(userId);
    }
}
