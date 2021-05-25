package com.wushi.lade.tests.service.dm.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushi.lade.tests.entity.dm.Address;
import com.wushi.lade.tests.mapper.dm.DmTestMapper;
import com.wushi.lade.tests.service.dm.DmService;
import org.springframework.stereotype.Service;

/**
 * @author wushi
 * @date 2020/2/3 17:23
 */
@Service
@DS("dm")
public class DmServiceImpl extends ServiceImpl<DmTestMapper, Address> implements DmService {
}
