package com.wushi.lade.tests.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wushi.lade.tests.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wushi
 * @date 2020-01-07
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * left join查询
     *
     * @param userId 用户ID
     * @return com.wushi.lade.tests.entity.UserVO
     * @author wushi
     * @date 2020/1/9 9:58
     */
    List<Map<String, Object>> selectWithAccount(String userId);
}
