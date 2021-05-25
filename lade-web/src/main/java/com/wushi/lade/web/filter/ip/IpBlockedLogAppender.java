package com.wushi.lade.web.filter.ip;

import com.wushi.lade.web.entity.IpBlockedInfo;

/**
 * ip拦截记录器
 *
 * @author wushi
 */
public interface IpBlockedLogAppender {
    void doAppend(IpBlockedInfo info);
}
