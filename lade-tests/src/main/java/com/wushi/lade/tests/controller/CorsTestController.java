package com.wushi.lade.tests.controller;

import com.wushi.lade.common.enums.ErrorCode;
import com.wushi.lade.common.exceptions.BusinessException;
import com.wushi.lade.common.utils.PathUtils;
import com.wushi.lade.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/cors")
public class CorsTestController extends BaseController {
    private final  static Logger LOGGER = LoggerFactory.getLogger(CorsTestController.class);
    @RequestMapping(value = "/a")
    @ResponseBody
    public String cors() {
        LOGGER.info("===================cors");
        return PathUtils.getBasePath();
    }


    @RequestMapping(value = "/b")
    @ResponseBody
    public void bb() throws Exception {
        throw new Exception("测试");
    }


}
