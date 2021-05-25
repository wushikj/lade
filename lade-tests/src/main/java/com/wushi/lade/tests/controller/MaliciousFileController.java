package com.wushi.lade.tests.controller;

import com.wushi.lade.common.enums.ErrorCode;
import com.wushi.lade.web.controller.BaseController;
import com.wushi.lade.web.result.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wushi
 * @date 2020/1/20 15:12
 */
@RestController
@RequestMapping("/malicious_file")
public class MaliciousFileController extends BaseController {
    private final  static Logger LOGGER = LoggerFactory.getLogger(MaliciousFileController.class);
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public R uploadFile(@RequestParam("file") MultipartFile file, String param) {
        LOGGER.info("multipartFiles=========" + file);
        if (file == null) {
            return R.ok(ErrorCode.File_UploadFailure);
        }
        LOGGER.info(/*multipartFiles.length + */"=========" + param);
        return R.ok("111111");
    }
}
