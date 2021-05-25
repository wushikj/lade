package com.wushi.lade.tests.mapper.sqlserver;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.JsonNode;

public interface UserMapper {


    /**
     * @param page
     * @author wushi
     * @date 2020/2/28 10:04
     * @description
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
    */
    IPage<JsonNode> selectUsers(IPage page);
}
