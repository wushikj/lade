package com.wushi.lade.tenant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wushi.lade.tenant.resolver.ITenantValidate;
import com.wushi.lade.tests.entity.Student;
import com.wushi.lade.tests.mapper.StudentMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author wushi
 * @date 2020/1/18 16:34
 * @description
 */
@SpringBootTest
public class TenantTest {


    @Autowired
    private ITenantValidate tenantValidate;

    @Resource
    private StudentMapper dao;


    @Test
    public void tenantInsertTest() throws ClassNotFoundException {

        Class<?> aClass = Class.forName("com.wushi.lade.tenant.TenantTest");

        Student student = new Student();
        String studentName = UUID.randomUUID().toString();
        student.setStudentName(studentName);
        student.setAge(10);
        student.setBirthday(new Date());
        student.setSex(1);
        dao.insert(student);
        QueryWrapper<Student> query = new QueryWrapper<>();
        query.lambda().eq(Student::getStudentName, student.getStudentName());
        Student insert = dao.selectOne(query);
        Assertions.assertEquals(insert.getTenantId(), "1");
    }

}
