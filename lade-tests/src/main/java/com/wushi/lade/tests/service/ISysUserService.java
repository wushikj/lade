package com.wushi.lade.tests.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wushi.lade.tests.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wushi
 * @date 2020-01-07
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 插入sysUser到数据库
     *
     * @param sysUser 用户实体类
     * @return int 返回影响行数
     * @author wushi
     * @date 2020/1/7 16:47
     */
    int insertReturnAffectRow(SysUser sysUser);

    /**
     * left join查询
     *
     * @param userId 用户ID
     * @return com.wushi.lade.tests.entity.UserVO
     * @author wushi
     * @date 2020/1/9 10:00
     */
    List<Map<String, Object>> selectWithAccount(String userId);
}
