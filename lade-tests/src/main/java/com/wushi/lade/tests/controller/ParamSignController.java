package com.wushi.lade.tests.controller;

import com.wushi.lade.common.exceptions.BusinessException;
import com.wushi.lade.tests.entity.Student;
import com.wushi.lade.web.controller.BaseController;
import com.wushi.lade.web.result.ContentResult;
import com.wushi.lade.web.result.FileResult;
import com.wushi.lade.web.result.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wushi
 * @date 2020/3/17 16:28
 * @description
 */
@RequestMapping("api/v1/sign")
@RestController
public class ParamSignController extends BaseController {

    @GetMapping("error1")
    public R error() throws Exception {
        throw new Exception("抛出异常");
    }

    @GetMapping
    public R<Map<String, Object>> gets() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", 123);
        map.put("userName", "789fea");
        return R.ok(map);
    }

    @GetMapping("action")
    public R actionResult() {
        Student student = new Student();
        student.setStudentName("123456");
        return R.ok(student);
    }

    @GetMapping("action1")
    public R<Student> actionResult1() {
        Student student = new Student();
        student.setStudentName("123456");
        return R.ok(student);
    }

    @GetMapping("file")
    public FileResult fileResult1(String fileName) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("D:\\文档\\文档管理配置说明.doc");

        return fileResult(fileInputStream, MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    @GetMapping("html")
    public ContentResult html(String fileName) throws FileNotFoundException {
        String html = "<p style=\"\n" +
                "    font-size: 20px;\n" +
                "\">123456</p>";

        return htmlResult(html);
    }

    @GetMapping("xml")
    public ContentResult xml(String fileName) throws FileNotFoundException {
        String html = "<config><file>123456</file><name>四委办局</name></config>";
        return xmlResult(html);
    }

    @GetMapping("global")
    public R global(String fileName) throws BusinessException {
        throw new BusinessException("123456", HttpStatus.UNAUTHORIZED.value());
    }

    @GetMapping("global/ex")
    public R ex(String fileName) throws Exception {
        throw new Exception("546896");
    }
}
