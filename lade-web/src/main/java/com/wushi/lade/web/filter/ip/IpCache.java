package com.wushi.lade.web.filter.ip;


import com.wushi.lade.web.enums.IpFilterMode;
import com.wushi.lade.web.utils.IpUtils;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IPAddressString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wushi
 */
public class IpCache {


    private List<IPAddressSeqRange> ipAddressSeqRangeList = new ArrayList<>();

    public void initIp(IpFilterProperties ipFilterProperties) throws Exception {
        if (ipFilterProperties.getMode() == IpFilterMode.ALLOWED) {
            initIpAddressInfo(ipFilterProperties.getAllowed());
        } else if (ipFilterProperties.getMode() == IpFilterMode.BLOCKED) {
            initIpAddressInfo(ipFilterProperties.getBlocked());
        }
    }


    private void initIpAddressInfo(String ipRules) {
        String[] ipStrings = ipRules.split(",");
        //再判断其中是否包含"-"
        for (String ipString : ipStrings) {
            String[] ips = ipString.split("-");
            if (ips.length == 0) {
                continue;
            }
            if (ips.length == 1) {
                //单个IP
                addIpAddressSeqRange(ips[0], ips[0]);
            } else if (ips.length == 2) {
                addIpAddressSeqRange(ips[0], ips[1]);
            }
        }
    }

    private void addIpAddressSeqRange(String lowerIp, String upperIp) {
        try {
            IPAddress lower = new IPAddressString(lowerIp).toAddress();
            IPAddress upper = new IPAddressString(upperIp).toAddress();
            IPAddressSeqRange range = lower.toSequentialRange(upper);
            ipAddressSeqRangeList.add(range);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isContainsIp(String ip) {
        if (!IpUtils.isIpAddress(ip)) {
            return false;
        }
        try {
            IPAddress addr = new IPAddressString(ip).toAddress();
            for (IPAddressSeqRange ipAddressSeqRange : ipAddressSeqRangeList) {
                if (ipAddressSeqRange.contains(addr)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
