package com.wushi.lade.tests.controller;

import com.wushi.lade.web.controller.BaseController;
import com.wushi.lade.web.result.ContentResult;
import com.wushi.lade.web.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Objects;

/**
 * @author wushi
 */
@RestController
@RequestMapping("/web")
public class WebTestController extends BaseController {

    /**
     * 异常测试
     *
     * @param test
     * @return
     */
    @GetMapping("/error")
    public R exceptionTest(int test) {
        if (test == 0) {
            return R.fail("failed");
        }
        return R.fail("success");
    }

    /**
     * 异常测试
     *
     * @param test
     * @return
     */
    @GetMapping("/errorTest")
    public R errorTest(int test) {
        if (test == 0) {
            return R.fail("failed");
        }
        return R.ok("success");
    }

    @RequestMapping("/html")
    public ContentResult htmlResult() {
        String html = "<!DOCTYPE html> <html> <head> <title>test</title> <meta name=\"keywords\" content=\"test\"> <meta name=\"description\" content=\"test website\"> </head> <body> <h1>My first webwite!</h1> </body> <footer> <p>By me</a> </footer> </html>";
        return htmlResult(html);
    }

    @RequestMapping("/success")
    public R SuccessResult() {
        return R.ok("test");
    }

    @RequestMapping("/xml")
    void xmlResult2() throws IOException {
        String xmlTo = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ufinterface roottag=\"voucher\" billtype=\"gl\" replace=\"Y\" receiver=\"1112\" sender=\"01\" isexchange=\"Y\" proc=\"add\" operation=\"req\" filename=\"e:\\1.xml\">";

        xmlTo = xmlTo + "<voucher>" + "<voucher_body>" + "body";

        xmlTo = xmlTo + "</voucher_body>" + "</voucher>" + "</ufinterface>";
        xmlResult(xmlTo);
    }

    @RequestMapping("/file1")
    void fileResult1() throws IOException {
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("test.xml")).getPath();
        path = URLDecoder.decode(path, "UTF-8");
        FileInputStream file = new FileInputStream(new File(path));
        fileResult(file, "test1");
    }

    @RequestMapping("/file3")
    void fileResult3() throws IOException {
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("test.xml")).getPath();
        path = URLDecoder.decode(path, "UTF-8");
        FileInputStream file = new FileInputStream(new File(path));
        fileResult(file, "application/octet-stream", "test1");
    }
}
