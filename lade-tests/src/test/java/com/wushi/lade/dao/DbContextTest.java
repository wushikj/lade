package com.wushi.lade.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.wushi.lade.LadeTestsApplication;
import com.wushi.lade.dao.interfaces.DbContext;
import com.wushi.lade.dao.interfaces.UnitOfWork;
import com.wushi.lade.tests.entity.Student;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.*;

/**
 * @author wushi
 * @date 2020/3/25 10:59
 * @description
 */
@SpringBootTest(classes = {LadeTestsApplication.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DbContextTest {

    @Autowired
    private DbContext dbContext;

    private static DruidDataSource dataSource;

    private static DruidDataSource oracle;

    private static DruidDataSource mysql;

    private static DruidDataSource dm;

    private static DruidDataSource gauss;

    private static DruidDataSource pg;


    static {
        dataSource = new DruidDataSource();
        dataSource.setName("conn");
        dataSource.setUrl("jdbc:sqlserver://;DatabaseName=WS_Unit_Test");
        dataSource.setUsername("");
        dataSource.setPassword("");

        oracle = new DruidDataSource();
        oracle.setName("oracle");
        oracle.setUrl("jdbc:oracle:thin:@:orcl");
        oracle.setUsername("");
        oracle.setPassword("");
        oracle.setDriverClassName("oracle.jdbc.OracleDriver");

        mysql = new DruidDataSource();
        String url = "jdbc:mysql:///ws_unit_test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
        String userName = "";
        String password = "";
        String name = String.valueOf(String.format("%s.%s.%s", url, userName, password).hashCode());
        mysql.setName(name);
        mysql.setUrl(url);
        mysql.setUsername(userName);
        mysql.setPassword(password);

        dm = new DruidDataSource();


        gauss = new DruidDataSource();

        pg = new DruidDataSource();
    }


    //sqlserver数据库测试
//    @Test
//    @Order(1)
//    public void insertTest() throws SQLException {
//        Student student = new Student();
//        student.setStudentName("feaf");
//        student.setAge(20);
//        student.setBirthday(new Date());
//        student.setIdCard("132456");
//        student.setSex(1);
//        String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
//        int result = dbContext.insert(dataSource, insertSql, student);
//        Assertions.assertEquals(1L, result);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("studentName", "feagearga");
//        map.put("age", 20);
//        map.put("idCard", "4586132");
//        map.put("birthday", new Date());
//        map.put("sex", 0);
//        int result1 = dbContext.insert(dataSource, insertSql, map);
//        Assertions.assertEquals(1L, result1);
//
//
//    }
//
//    @Test
//    @Order(2)
//    public void insertObjectListTest() throws SQLException {
//        List<Student> list = new ArrayList<>();
//        Student student = new Student();
//        student.setStudentName("feaf");
//        student.setAge(20);
//        student.setBirthday(new Date());
//        student.setIdCard("132456");
//        student.setSex(1);
//        list.add(student);
//        Student student1 = new Student();
//        student1.setStudentName("feaf789fea");
//        student1.setAge(20);
//        student1.setBirthday(new Date());
//        student1.setIdCard("132456fea");
//        student1.setSex(1);
//        list.add(student1);
//        String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
//        int result = dbContext.insert(dataSource, insertSql, list);
//        Assertions.assertEquals(2L, result);
//
//        List<Map<String, Object>> list1 = new ArrayList<>();
//        Map<String, Object> student2 = new HashMap<>();
//        student2.put("studentName", "feagearga");
//        student2.put("age", 20);
//        student2.put("idCard", "4586132");
//        student2.put("birthday", new Date());
//        student2.put("sex", 0);
//        list1.add(student2);
//        Map<String, Object> student3 = new HashMap<>();
//        student3.put("studentName", "studentmaplist");
//        student3.put("age", 20);
//        student3.put("idCard", "4586132");
//        student3.put("birthday", new Date());
//        student3.put("sex", 0);
//        list1.add(student3);
//        int result1 = dbContext.insert(dataSource, insertSql, list1);
//        Assertions.assertEquals(2L, result1);
//
//    }
//
//    @Test
//    @Order(3)
//    public void inertReturning() throws SQLException {
//        String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
//
//        Student student = new Student();
//        student.setStudentName("789456123");
//        student.setAge(20);
//        student.setBirthday(new Date());
//        student.setIdCard("132456");
//        student.setSex(1);
//        int flag = dbContext.insert(dataSource, insertSql, student, "studentId", "student_id");
//        Assertions.assertTrue(flag > 0 && student.getStudentId() > 0);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("studentId", null);
//        map.put("studentName", "feagearga");
//        map.put("age", 20);
//        map.put("idCard", "4586132");
//        map.put("birthday", new Date());
//        map.put("sex", 0);
//        int flag1 = dbContext.insert(dataSource, insertSql, map, "studentId", "student_id");
//        Assertions.assertTrue(flag1 > 0 && Integer.valueOf(map.get("studentId").toString()) > 0);
//
//        Student student1 = new Student();
//        student1.setStudentName("789456123");
//        student1.setAge(20);
//        student1.setBirthday(new Date());
//        student1.setIdCard("132456");
//        student1.setSex(1);
//        int flag2 = dbContext.insert("sqlserver", insertSql, student1, "studentId", "student_id");
//        Assertions.assertTrue(flag2 > 0 && student1.getStudentId() > 0);
//
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("studentId", null);
//        map1.put("studentName", "feagearga");
//        map1.put("age", 20);
//        map1.put("idCard", "4586132");
//        map1.put("birthday", new Date());
//        map1.put("sex", 0);
//        int flag3 = dbContext.insert("sqlserver", insertSql, map1, "studentId", "student_id");
//        Assertions.assertTrue(flag3 > 0 && Integer.valueOf(map1.get("studentId").toString()) > 0);
//    }
//
//
//    @Test
//    @Order(4)
//    public void transactionTest() throws Exception {
//        Student student = new Student();
//        student.setStudentName(UUID.randomUUID().toString());
//        UnitOfWork unitOfWork = dbContext.getUnitOfWork(dataSource);
//        unitOfWork.begin();
//        try {
//            student.setAge(20);
//            student.setBirthday(new Date());
//            student.setIdCard("132456");
//            student.setSex(1);
//            String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
//            int result = dbContext.insert(unitOfWork.getDataSource(), insertSql, student);
//            unitOfWork.commit();
//        } catch (Exception ex) {
//            unitOfWork.rollback();
//        } finally {
//            String selectStr = "select * from ws_student where student_name=#{studentName}";
//            Student stu = dbContext.querySingle(unitOfWork.getDataSource(), selectStr, student, Student.class);
//            Assertions.assertTrue(stu != null);
//        }
//    }
//
//    @Test
//    @Order(5)
//    public void transaction1Test() throws Exception {
//        Student student = new Student();
//        student.setStudentName(UUID.randomUUID().toString());
//        UnitOfWork unitOfWork = dbContext.getUnitOfWork(dataSource);
//        unitOfWork.begin();
//        try {
//            student.setAge(20);
//            student.setBirthday(new Date());
//            student.setIdCard("132456");
//            student.setSex(1);
//            String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
//            int result = dbContext.insert(unitOfWork.getDataSource(), insertSql, student);
//            throw new Exception("事务测试");
//        } catch (Exception ex) {
//            unitOfWork.rollback();
//        } finally {
//            String selectStr = "select * from ws_student where student_name=#{studentName}";
//            Student stu = dbContext.querySingle(unitOfWork.getDataSource(), selectStr, student, Student.class);
//            Assertions.assertTrue(stu == null);
//        }
//    }
//
//    @Test
//    @Order(6)
//    public void executeScalarTest() throws SQLException {
//        String sql = "select top 1 student_name from ws_student";
//        String studentName = dbContext.executeScalar(dataSource, sql, null, String.class);
//        Assertions.assertTrue(StringUtils.isNotBlank(studentName));
//
//        String name = dbContext.executeScalar("sqlserver", sql, null, String.class);
//        Assertions.assertTrue(StringUtils.isNotBlank(studentName));
//    }
//
//    @Test
//    @Order(7)
//    public void queryTest() throws SQLException {
//        String sql = "select * from ws_student";
//        List<Student> list = dbContext.query(dataSource, sql, null, Student.class);
//
//        List<Student> list1 = dbContext.query("sqlserver", sql, null, Student.class);
//
//        Assertions.assertTrue(list.size() == list1.size() && list.size() > 0);
//
//        List<Map<String, Object>> maps = dbContext.query(dataSource, sql, null);
//        List<Map<String, Object>> maps1 = dbContext.query("sqlserver", sql, null);
//        Assertions.assertTrue(maps1.size() == maps.size() && maps.size() > 0);
//
//    }
//
//    @Test
//    @Order(8)
//    public void updateTest() throws SQLException {
//        String sql = "select top 1 * from ws_student";
//        Student student = dbContext.querySingle(dataSource, sql, null, Student.class);
//
//        Student student1 = dbContext.querySingle("sqlserver", sql, null, Student.class);
//
//        Assertions.assertTrue(student != null && student1 != null);
//
//
//        student.setStudentName("updateTest");
//        int result = dbContext.update(dataSource, "update ws_student set student_name=#{studentName} where student_id=#{studentId}", student);
//        int result1 = dbContext.update("sqlserver", "update ws_student set student_name=#{studentName} where student_id=#{studentId}", student);
//        Student student2 = dbContext.querySingle(dataSource, "select * from ws_student where student_id=#{studentId}", student, Student.class);
//        Assertions.assertTrue(result == result1 && student2.getStudentName().equals("updateTest"));
//    }
//
//    @Test
//    @Order(9)
//    public void deleteTest() throws SQLException {
//        String sql = "select top 1 * from ws_student";
//        Student student = dbContext.querySingle(dataSource, sql, null, Student.class);
//
//        int result = dbContext.delete(dataSource, "delete from ws_student where student_id=#{studentId}", student);
//        Assertions.assertTrue(result > 0);
//    }
//
//    @Test
//    @Order(10)
//    public void deleteTest1() throws SQLException {
//        String sql = "select top 2 * from ws_student";
//        List<Student> student = dbContext.query(dataSource, sql, null, Student.class);
//
//        int result = dbContext.delete(dataSource, "delete from ws_student where student_id=#{studentId}", student);
//        Assertions.assertTrue(result > 0);
//    }

    //oracle数据库测试

    @Test
    @Order(11)
    public void insertOracleTest() throws SQLException {
        Student student = new Student();
        student.setStudentName("feaf");
        student.setAge(20);
        student.setBirthday(new Date());
        student.setIdCard("132456");
        student.setSex(1);
        String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
        int result = dbContext.insert(oracle, insertSql, student);
        Assertions.assertTrue(result > 0);

        Map<String, Object> map = new HashMap<>();
        map.put("studentName", "feagearga");
        map.put("age", 20);
        map.put("idCard", "4586132");
        map.put("birthday", new Date());
        map.put("sex", 0);
        int result1 = dbContext.insert(oracle, insertSql, map);
        Assertions.assertTrue(result1 > 0);
    }

    @Test
    @Order(12)
    public void insertObjectListOracleTest() throws SQLException {
        List<Student> list = new ArrayList<>();
        Student student = new Student();
        student.setStudentName("feaf");
        student.setAge(20);
        student.setBirthday(new Date());
        student.setIdCard("132456");
        student.setSex(1);
        list.add(student);
        Student student1 = new Student();
        student1.setStudentName("feaf789fea");
        student1.setAge(20);
        student1.setBirthday(new Date());
        student1.setIdCard("132456fea");
        student1.setSex(1);
        list.add(student1);
        String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
        int result = dbContext.insert(oracle, insertSql, list, "studentId", "student_id");
        Assertions.assertEquals(2L, result);

        List<Map<String, Object>> list1 = new ArrayList<>();
        Map<String, Object> student2 = new HashMap<>();
        student2.put("studentName", "feagearga");
        student2.put("age", 20);
        student2.put("idCard", "4586132");
        student2.put("birthday", new Date());
        student2.put("sex", 0);
        list1.add(student2);
        Map<String, Object> student3 = new HashMap<>();
        student3.put("studentName", "studentmaplist");
        student3.put("age", 20);
        student3.put("idCard", "4586132");
        student3.put("birthday", new Date());
        student3.put("sex", 0);
        list1.add(student3);
        int result1 = dbContext.insert(oracle, insertSql, list1, "studentId", "student_id");
        Assertions.assertEquals(2L, result1);
    }

    @Test
    @Order(13)
    public void inertReturningOracleTest() throws SQLException {
        String insertSql = "INSERT INTO WS_STUDENT(STUDENT_NAME,AGE,ID_CARD,BIRTHDAY,SEX) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";


        Student student = new Student();
        student.setStudentName("789456123");
        student.setAge(20);
        student.setBirthday(new Date());
        student.setIdCard("132456");
        student.setSex(1);
        int result = dbContext.insert(oracle, insertSql, student, "studentId", "student_id");
        Assertions.assertTrue(result > 0 && student.getStudentId() > 0);

        Map<String, Object> map = new HashMap<>();
        map.put("studentName", "feagearga");
        map.put("age", 20);
        map.put("idCard", "4586132");
        map.put("birthday", new Date());
        map.put("sex", 0);
        Number result1 = dbContext.insert(oracle, insertSql, map, "studentId", "student_id");
        Assertions.assertTrue(result1.intValue() > 0 && Integer.valueOf(map.get("studentId").toString()) > 0);

        Student student1 = new Student();
        student1.setStudentName("789456123");
        student1.setAge(20);
        student1.setBirthday(new Date());
        student1.setIdCard("132456");
        student1.setSex(1);
        Number result2 = dbContext.insert("oracle", insertSql, student1, "studentId", "student_id");
        Assertions.assertTrue(result2.intValue() > 0 && student1.getStudentId() > 0);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("studentName", "feagearga");
        map1.put("age", 20);
        map1.put("idCard", "4586132");
        map1.put("birthday", new Date());
        map1.put("sex", 0);
        Number result3 = dbContext.insert("oracle", insertSql, map1, "studentId", "student_id");
        Assertions.assertTrue(result3.intValue() > 0 && Integer.valueOf(map1.get("studentId").toString()) > 0);
    }

    @Test
    @Order(14)
    public void transactionOracleTest() throws Exception {
        Student student = new Student();
        student.setStudentName(UUID.randomUUID().toString());
        UnitOfWork unitOfWork = dbContext.getUnitOfWork(oracle);
        unitOfWork.begin();
        try {
            student.setAge(20);
            student.setBirthday(new Date());
            student.setIdCard("132456");
            student.setSex(1);
            String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
            int result = dbContext.insert(unitOfWork.getDataSource(), insertSql, student);
            unitOfWork.commit();
        } catch (Exception ex) {
            unitOfWork.rollback();
        } finally {
            String selectStr = "select * from ws_student where student_name=#{studentName}";
            Student stu = dbContext.querySingle(unitOfWork.getDataSource(), selectStr, student, Student.class);
            Assertions.assertTrue(stu != null);
        }
    }

    @Test
    @Order(15)
    public void transactionOracle1Test() throws Exception {
        Student student = new Student();
        student.setStudentName(UUID.randomUUID().toString());
        UnitOfWork unitOfWork = dbContext.getUnitOfWork(oracle);
        unitOfWork.begin();
        try {
            student.setAge(20);
            student.setBirthday(new Date());
            student.setIdCard("132456");
            student.setSex(1);
            String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
            int result = dbContext.insert(unitOfWork.getDataSource(), insertSql, student);
            throw new Exception("事务测试");
        } catch (Exception ex) {
            unitOfWork.rollback();
        } finally {
            String selectStr = "select * from ws_student where student_name=#{studentName}";
            Student stu = dbContext.querySingle(unitOfWork.getDataSource(), selectStr, student, Student.class);
            Assertions.assertTrue(stu == null);
        }
    }

    @Test
    @Order(16)
    public void executeScalarOracleTest() throws SQLException {
        String sql = "select student_name from ws_student where rownum<=1";

        String studentName = dbContext.executeScalar(oracle, sql, null, String.class);
        Assertions.assertTrue(StringUtils.isNotBlank(studentName));

        String name = dbContext.executeScalar("oracle", sql, null, String.class);
        Assertions.assertTrue(StringUtils.isNotBlank(studentName));
    }

    @Test
    @Order(17)
    public void queryOracleTest() throws SQLException {
        String sql = "select * from ws_student";
        List<Student> list = dbContext.query(oracle, sql, null, Student.class);

        List<Student> list1 = dbContext.query("oracle", sql, null, Student.class);

        Assertions.assertTrue(list.size() == list1.size() && list.size() > 0);

        List<Map<String, Object>> maps = dbContext.query(oracle, sql, null);
        List<Map<String, Object>> maps1 = dbContext.query("oracle", sql, null);
        Assertions.assertTrue(maps1.size() == maps.size() && maps.size() > 0);

    }

    @Test
    @Order(16)
    public void updateOracleTest() throws SQLException {
        String sql = "select * from ws_student where rownum<=1";
        Student student = dbContext.querySingle(oracle, sql, null, Student.class);

        Student student1 = dbContext.querySingle("oracle", sql, null, Student.class);

        Assertions.assertTrue(student != null && student1 != null);


        student.setStudentName("updateTest");
        int result = dbContext.update(oracle, "update ws_student set student_name=#{studentName} where student_id=#{studentId}", student);
        int result1 = dbContext.update("oracle", "update ws_student set student_name=#{studentName} where student_id=#{studentId}", student);
        Student student2 = dbContext.querySingle(oracle, "select * from ws_student where student_id=#{studentId}", student, Student.class);
        Assertions.assertTrue(result == result1 && student2.getStudentName().equals("updateTest"));
    }

    @Test
    @Order(18)
    public void deleteOracleTest() throws SQLException {
        String sql = "select * from ws_student where rownum<=1";
        Student student = dbContext.querySingle(oracle, sql, null, Student.class);

        int result = dbContext.delete(oracle, "delete from ws_student where student_id=#{studentId}", student);
        Assertions.assertTrue(result > 0);
    }

    @Test
    @Order(19)
    public void deleteOracleTest1() throws SQLException {
        String sql = "select * from ws_student where rownum<=2";
        List<Student> student = dbContext.query(oracle, sql, null, Student.class);

        int result = dbContext.delete(oracle, "delete from ws_student where student_id=#{studentId}", student);
        Assertions.assertTrue(result > 0);
    }

    //mysql数据库测试

    @Test
    @Order(20)
    public void insertMySqlTest() throws SQLException {
        Student student = new Student();
        student.setStudentName("feaf");
        student.setAge(20);
        student.setBirthday(new Date());
        student.setIdCard("132456");
        student.setSex(1);
        String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
        int result = dbContext.insert(mysql, insertSql, student, "studentId", "student_id");
        Assertions.assertEquals(1L, result);

        Map<String, Object> map = new HashMap<>();
        map.put("studentName", "feagearga");
        map.put("age", 20);
        map.put("cardId", "4586132");
        map.put("birthday", new Date());
        map.put("sex", 0);
        int result1 = dbContext.insert(mysql, insertSql, map);
        Assertions.assertEquals(1L, result1);
    }

    @Test
    @Order(21)
    public void insertObjectListMySqlTest() throws SQLException {
        List<Student> list = new ArrayList<>();
        Student student = new Student();
        student.setStudentName("feaf");
        student.setAge(20);
        student.setBirthday(new Date());
        student.setIdCard("132456");
        student.setSex(1);
        list.add(student);
        Student student1 = new Student();
        student1.setStudentName("feaf789fea");
        student1.setAge(20);
        student1.setBirthday(new Date());
        student1.setIdCard("132456fea");
        student1.setSex(1);
        list.add(student1);
        String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
        int result = dbContext.insert(mysql, insertSql, list, "studentId", "student_id");
        Assertions.assertEquals(2L, result);

        List<Map<String, Object>> list1 = new ArrayList<>();
        Map<String, Object> student2 = new HashMap<>();
        student2.put("studentName", "feagearga");
        student2.put("age", 20);
        student2.put("cardId", "4586132");
        student2.put("birthday", new Date());
        student2.put("sex", 0);
        list1.add(student2);
        Map<String, Object> student3 = new HashMap<>();
        student3.put("studentName", "studentmaplist");
        student3.put("age", 20);
        student3.put("cardId", "4586132");
        student3.put("birthday", new Date());
        student3.put("sex", 0);
        list1.add(student3);
        int result1 = dbContext.insert(mysql, insertSql, list1);
        Assertions.assertEquals(2L, result1);
    }

    @Test
    @Order(22)
    public void inertReturningMySqlTest() throws SQLException {
        String insertSql = "INSERT INTO ws_STUDENT(STUDENT_NAME,AGE,ID_CARD,BIRTHDAY,SEX) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";


        Student student = new Student();
        student.setStudentName("789456123");
        student.setAge(20);
        student.setBirthday(new Date());
        student.setIdCard("132456");
        student.setSex(1);
        int result = dbContext.insert(mysql, insertSql, student, "studentId", "student_id");
        Assertions.assertTrue(result > 0 && student.getStudentId() > 0);

        Map<String, Object> map = new HashMap<>();
        map.put("studentName", "feagearga");
        map.put("age", 20);
        map.put("cardId", "4586132");
        map.put("birthday", new Date());
        map.put("sex", 0);
        int result1 = dbContext.insert(mysql, insertSql, map, "studentId", "student_id");
        Assertions.assertTrue(result1 > 0 && Integer.valueOf(map.get("studentId").toString()) > 0);

        Student student1 = new Student();
        student1.setStudentName("789456123");
        student1.setAge(20);
        student1.setBirthday(new Date());
        student1.setIdCard("132456");
        student1.setSex(1);
        int result2 = dbContext.insert("mysql", insertSql, student1, "studentId", "student_id");
        Assertions.assertTrue(result2 > 0 && student1.getStudentId() > 0);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("studentName", "feagearga");
        map1.put("age", 20);
        map1.put("cardId", "4586132");
        map1.put("birthday", new Date());
        map1.put("sex", 0);
        int result3 = dbContext.insert("mysql", insertSql, map1, "studentId", "student_id");
        Assertions.assertTrue(result3 > 0 && Integer.valueOf(map1.get("studentId").toString()) > 0);
    }

    @Test
    @Order(23)
    public void transactionMySqlTest() throws Exception {
        Student student = new Student();
        student.setStudentName(UUID.randomUUID().toString());
        UnitOfWork unitOfWork = dbContext.getUnitOfWork(mysql);
        unitOfWork.begin();
        try {
            student.setAge(20);
            student.setBirthday(new Date());
            student.setIdCard("132456");
            student.setSex(1);
            String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
            int result = dbContext.insert(unitOfWork.getDataSource(), insertSql, student);
            unitOfWork.commit();
        } catch (Exception ex) {
            unitOfWork.rollback();
        } finally {
            String selectStr = "select * from ws_student where student_name=#{studentName}";
            Student stu = dbContext.querySingle(unitOfWork.getDataSource(), selectStr, student, Student.class);
            Assertions.assertTrue(stu != null);
        }
    }

    @Test
    @Order(24)
    public void transactionMySql1Test() throws Exception {
        Student student = new Student();
        student.setStudentName(UUID.randomUUID().toString());
        UnitOfWork unitOfWork = dbContext.getUnitOfWork(mysql);
        unitOfWork.begin();
        try {
            student.setAge(20);
            student.setBirthday(new Date());
            student.setIdCard("132456");
            student.setSex(1);
            String insertSql = "insert into ws_student(student_name,age,id_card,birthday,sex) values(#{studentName},#{age},#{idCard},#{birthday},#{sex})";
            int result = dbContext.insert(unitOfWork.getDataSource(), insertSql, student);
            throw new Exception("事务测试");
        } catch (Exception ex) {
            unitOfWork.rollback();
        } finally {
            String selectStr = "select * from ws_student where student_name=#{studentName}";
            Student stu = dbContext.querySingle(unitOfWork.getDataSource(), selectStr, student, Student.class);
            Assertions.assertTrue(stu == null);
        }
    }

    @Test
    @Order(25)
    public void executeScalarMySqlTest() throws SQLException {
        String sql = "select student_name from ws_student   limit 1";

        String studentName = dbContext.executeScalar(mysql, sql, null, String.class);
        Assertions.assertTrue(StringUtils.isNotBlank(studentName));

        String name = dbContext.executeScalar("mysql", sql, null, String.class);
        Assertions.assertTrue(StringUtils.isNotBlank(studentName));
    }

    @Test
    @Order(26)
    public void queryMySqlTest() throws SQLException {
        String sql = "select * from ws_student";
        List<Student> list = dbContext.query(mysql, sql, null, Student.class);

        List<Student> list1 = dbContext.query("mysql", sql, null, Student.class);

        Assertions.assertTrue(list.size() == list1.size() && list.size() > 0);

        List<Map<String, Object>> maps = dbContext.query(mysql, sql, null);
        List<Map<String, Object>> maps1 = dbContext.query("mysql", sql, null);
        Assertions.assertTrue(maps1.size() == maps.size() && maps.size() > 0);

    }

    @Test
    @Order(27)
    public void querySingleMySqlTest() throws SQLException {
        String sql = "select * from ws_student limit 1";
        Student list = dbContext.querySingle(mysql, sql, null, Student.class);

        Student list1 = dbContext.querySingle("mysql", sql, null, Student.class);


        Map<String, Object> maps = dbContext.querySingle(mysql, sql, null);
        Map<String, Object> maps1 = dbContext.querySingle("mysql", sql, null);

    }

    @Test
    @Order(28)
    public void updateMySqlTest() throws SQLException {
        String sql = "select * from ws_student  limit 1";
        Student student = dbContext.querySingle(mysql, sql, null, Student.class);

        Student student1 = dbContext.querySingle("mysql", sql, null, Student.class);

        Assertions.assertTrue(student != null && student1 != null);


        student.setStudentName("updateTest");
        int result = dbContext.update(mysql, "update ws_student set student_name=#{studentName} where student_id=#{studentId}", student);
        int result1 = dbContext.update("mysql", "update ws_student set student_name=#{studentName} where student_id=#{studentId}", student);
        Student student2 = dbContext.querySingle(mysql, "select * from ws_student where student_id=#{studentId}", student, Student.class);
        Assertions.assertTrue(result == result1 && student2.getStudentName().equals("updateTest"));
    }

    @Test
    @Order(29)
    public void deleteMySqlTest() throws SQLException {
        String sql = "select * from ws_student  limit 1";
        Student student = dbContext.querySingle(mysql, sql, null, Student.class);

        int result = dbContext.delete(mysql, "delete from ws_student where student_id=#{studentId}", student);
        Assertions.assertTrue(result > 0);
    }

    @Test
    @Order(30)
    public void deleteMySqlTest1() throws SQLException {
        String sql = "select * from ws_student limit 2";
        List<Student> student = dbContext.query(mysql, sql, null, Student.class);

        int result = dbContext.delete(mysql, "delete from ws_student where student_id=#{studentId}", student);
        Assertions.assertTrue(result > 0);
    }

//    //达梦数据库测试
//
//    @Test
//    @Order(19)
//    public void insertDmTest() {
//        Student student = new Student();
//        student.setStudentName("feaf");
//        student.setAge(20);
//        student.setBirthday(new Date());
//        student.setIdCard("132456");
//        student.setSex(1);
//        String insertSql = "insert into ws_student(student_name,age,cardid,birthday,sex) values(:studentName,:age,:cardId,:birthday,:sex)";
//        int result = dbContext.execute(dm, insertSql, student);
//        Assertions.assertEquals(1L, result);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("studentName", "feagearga");
//        map.put("age", 20);
//        map.put("cardId", "4586132");
//        map.put("birthday", new Date());
//        map.put("sex", 0);
//        int result1 = dbContext.execute(dm, insertSql, map);
//        Assertions.assertEquals(1L, result1);
//    }
//
//    @Test
//    @Order(20)
//    public void insertObjectListDmTest() {
//        List<Student> list = new ArrayList<>();
//        Student student = new Student();
//        student.setStudentName("feaf");
//        student.setAge(20);
//        student.setBirthday(new Date());
//        student.setIdCard("132456");
//        student.setSex(1);
//        list.add(student);
//        Student student1 = new Student();
//        student1.setStudentName("feaf789fea");
//        student1.setAge(20);
//        student1.setBirthday(new Date());
//        student1.setIdCard("132456fea");
//        student1.setSex(1);
//        list.add(student1);
//        String insertSql = "insert into ws_student(student_name,age,cardid,birthday,sex) values(:studentName,:age,:cardId,:birthday,:sex)";
//        int result = dbContext.execute(dm, insertSql, list);
//        Assertions.assertEquals(2L, result);
//
//        List<Map<String, Object>> list1 = new ArrayList<>();
//        Map<String, Object> student2 = new HashMap<>();
//        student2.put("studentName", "feagearga");
//        student2.put("age", 20);
//        student2.put("cardId", "4586132");
//        student2.put("birthday", new Date());
//        student2.put("sex", 0);
//        list1.add(student2);
//        Map<String, Object> student3 = new HashMap<>();
//        student3.put("studentName", "studentmaplist");
//        student3.put("age", 20);
//        student3.put("cardId", "4586132");
//        student3.put("birthday", new Date());
//        student3.put("sex", 0);
//        list1.add(student3);
//        int result1 = dbContext.execute(dm, insertSql, list1);
//        Assertions.assertEquals(2L, result1);
//    }
//
//    @Test
//    @Order(21)
//    public void inertReturningDmTest() {
//        String insertSql = "INSERT INTO WS_STUDENT(STUDENT_NAME,AGE,CARDID,BIRTHDAY,SEX) values(:studentName,:age,:cardId,:birthday,:sex)";
//
//
//        Student student = new Student();
//        student.setStudentName("789456123");
//        student.setAge(20);
//        student.setBirthday(new Date());
//        student.setIdCard("132456");
//        student.setSex(1);
//        Number result = dbContext.executeReturning(dm, insertSql, student);
//        Assertions.assertTrue(result.intValue() > 0);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("studentName", "feagearga");
//        map.put("age", 20);
//        map.put("cardId", "4586132");
//        map.put("birthday", new Date());
//        map.put("sex", 0);
//        Number result1 = dbContext.executeReturning(dm, insertSql, map);
//        Assertions.assertTrue(result1.intValue() > 0);
//
//        Student student1 = new Student();
//        student1.setStudentName("789456123");
//        student1.setAge(20);
//        student1.setBirthday(new Date());
//        student1.setIdCard("132456");
//        student1.setSex(1);
//        Number result2 = dbContext.executeReturning("dm", insertSql, student1);
//        Assertions.assertTrue(result2.intValue() > 0);
//
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("studentName", "feagearga");
//        map1.put("age", 20);
//        map1.put("cardId", "4586132");
//        map1.put("birthday", new Date());
//        map1.put("sex", 0);
//        Number result3 = dbContext.executeReturning("dm", insertSql, map1);
//        Assertions.assertTrue(result3.intValue() > 0);
//    }
//
//    @Test
//    @Order(23)
//    public void unitOfWorkDmTest() {
//        UnitOfWork unitOfWork = dbContext.getUnitOfWork(dm);
//        unitOfWork.begin();
//        try {
//            Student student = new Student();
//            student.setStudentName("unitofwork");
//            student.setAge(20);
//            student.setBirthday(new Date());
//            student.setIdCard("132456");
//            student.setSex(1);
//            String insertSql = "insert into ws_student(student_name,age,cardid,birthday,sex) values(:studentName,:age,:cardId,:birthday,:sex)";
//            int result = dbContext.execute(unitOfWork.getDataSource(), insertSql, student);
//            unitOfWork.commit();
//            Assertions.assertTrue(result > 0);
//        } catch (Exception ex) {
//            unitOfWork.rollback();
//        }
//    }
//
//    @Test
//    @Order(24)
//    public void executeScalarDmTest() {
//        String sql = "select student_name from ws_student   limit 1";
//
//        String studentName = dbContext.executeScalar(dm, sql, null, String.class);
//        Assertions.assertTrue(StringUtils.isNotBlank(studentName));
//
//        String name = dbContext.executeScalar("dm", sql, null, String.class);
//        Assertions.assertTrue(StringUtils.isNotBlank(studentName));
//    }
//
//    @Test
//    @Order(25)
//    public void queryDmTest() {
//        String sql = "select * from ws_student";
//        List<Student> list = dbContext.query(dm, sql, null, Student.class);
//
//        List<Student> list1 = dbContext.query("dm", sql, null, Student.class);
//
//        Assertions.assertTrue(list.size() == list1.size() && list.size() > 0);
//
//        List<Map<String, Object>> maps = dbContext.query(dm, sql, null);
//        List<Map<String, Object>> maps1 = dbContext.query("dm", sql, null);
//        Assertions.assertTrue(maps1.size() == maps.size() && maps.size() > 0);
//
//    }
//
//    @Test
//    @Order(26)
//    public void updateDmTest() {
//        String sql = "select * from ws_student  limit 1";
//        Student student = dbContext.querySingle(dm, sql, null, Student.class);
//
//        Student student1 = dbContext.querySingle("dm", sql, null, Student.class);
//
//        Assertions.assertTrue(student != null && student1 != null);
//
//
//        student.setStudentName("updateTest");
//        int result = dbContext.execute(dm, "update ws_student set student_name=:studentName where student_id=:studentId", student);
//        int result1 = dbContext.execute("dm", "update ws_student set student_name=:studentName where student_id=:studentId", student);
//        Student student2 = dbContext.querySingle(dm, "select * from ws_student where student_id=:studentId", student, Student.class);
//        Assertions.assertTrue(result == result1 && student2.getStudentName().equals("updateTest"));
//    }
//
//    @Test
//    @Order(27)
//    public void deleteDmTest() {
//        String sql = "select * from ws_student  limit 1";
//        Student student = dbContext.querySingle(dm, sql, null, Student.class);
//
//        int result = dbContext.execute(dm, "delete from ws_student where student_id=:studentId", student);
//        Assertions.assertTrue(result > 0);
//    }
//
//    @Test
//    @Order(28)
//    public void deleteDmTest1() {
//        String sql = "select * from ws_student limit 2";
//        List<Student> student = dbContext.query(dm, sql, null, Student.class);
//
//        int result = dbContext.execute(dm, "delete from ws_student where student_id=:studentId", student);
//        Assertions.assertTrue(result > 0);
//    }
//
//    //高斯数据库测试
//
//    @Test
//    @Order(19)
//    public void insertGaussTest() {
//        Student student = new Student();
//        student.setStudentName("feaf");
//        student.setAge(20);
//        student.setBirthday(new Date());
//        student.setIdCard("132456");
//        student.setSex(1);
//        String insertSql = "insert into ws_student(student_name,age,cardid,birthday,sex) values(:studentName,:age,:cardId,:birthday,:sex)";
//        int result = dbContext.execute(gauss, insertSql, student);
//        Assertions.assertEquals(1L, result);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("studentName", "feagearga");
//        map.put("age", 20);
//        map.put("cardId", "4586132");
//        map.put("birthday", new Date());
//        map.put("sex", 0);
//        int result1 = dbContext.execute(gauss, insertSql, map);
//        Assertions.assertEquals(1L, result1);
//    }
//
//    @Test
//    @Order(20)
//    public void insertObjectListGaussTest() {
//        List<Student> list = new ArrayList<>();
//        Student student = new Student();
//        student.setStudentName("feaf");
//        student.setAge(20);
//        student.setBirthday(new Date());
//        student.setIdCard("132456");
//        student.setSex(1);
//        list.add(student);
//        Student student1 = new Student();
//        student1.setStudentName("feaf789fea");
//        student1.setAge(20);
//        student1.setBirthday(new Date());
//        student1.setIdCard("132456fea");
//        student1.setSex(1);
//        list.add(student1);
//        String insertSql = "insert into ws_student(student_name,age,cardid,birthday,sex) values(:studentName,:age,:cardId,:birthday,:sex)";
//        int result = dbContext.execute(gauss, insertSql, list);
//        Assertions.assertEquals(2L, result);
//
//        List<Map<String, Object>> list1 = new ArrayList<>();
//        Map<String, Object> student2 = new HashMap<>();
//        student2.put("studentName", "feagearga");
//        student2.put("age", 20);
//        student2.put("cardId", "4586132");
//        student2.put("birthday", new Date());
//        student2.put("sex", 0);
//        list1.add(student2);
//        Map<String, Object> student3 = new HashMap<>();
//        student3.put("studentName", "studentmaplist");
//        student3.put("age", 20);
//        student3.put("cardId", "4586132");
//        student3.put("birthday", new Date());
//        student3.put("sex", 0);
//        list1.add(student3);
//        int result1 = dbContext.execute(gauss, insertSql, list1);
//        Assertions.assertEquals(2L, result1);
//    }
//
//    @Test
//    @Order(21)
//    public void inertReturningGaussTest() {
//        String insertSql = "INSERT INTO WS_STUDENT(STUDENT_NAME,AGE,CARDID,BIRTHDAY,SEX) values(:studentName,:age,:cardId,:birthday,:sex)";
//
//
//        Student student = new Student();
//        student.setStudentName("789456123");
//        student.setAge(20);
//        student.setBirthday(new Date());
//        student.setIdCard("132456");
//        student.setSex(1);
//        Number result = dbContext.executeReturning(gauss, insertSql, student, returningOptions -> returningOptions.setSequenceName("SEQ_WS_STUDENT"));
//        Assertions.assertTrue(result.intValue() > 0);
//
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("studentName", "feagearga");
//        map.put("age", 20);
//        map.put("cardId", "4586132");
//        map.put("birthday", new Date());
//        map.put("sex", 0);
//        Number result1 = dbContext.executeReturning(gauss, insertSql, map, returningOptions -> returningOptions.setSequenceName("SEQ_WS_STUDENT"));
//        Assertions.assertTrue(result1.intValue() > 0);
//
//        Student student1 = new Student();
//        student1.setStudentName("789456123");
//        student1.setAge(20);
//        student1.setBirthday(new Date());
//        student1.setIdCard("132456");
//        student1.setSex(1);
//        Number result2 = dbContext.executeReturning("gauss", insertSql, student1, returningOptions -> returningOptions.setSequenceName("SEQ_WS_STUDENT"));
//        Assertions.assertTrue(result2.intValue() > 0);
//
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("studentName", "feagearga");
//        map1.put("age", 20);
//        map1.put("cardId", "4586132");
//        map1.put("birthday", new Date());
//        map1.put("sex", 0);
//        Number result3 = dbContext.executeReturning("gauss", insertSql, map1, returningOptions -> returningOptions.setSequenceName("SEQ_WS_STUDENT"));
//        Assertions.assertTrue(result3.intValue() > 0);
//    }
//
//    @Test
//    @Order(23)
//    public void unitOfWorkGaussTest() {
//        UnitOfWork unitOfWork = dbContext.getUnitOfWork(gauss);
//        unitOfWork.begin();
//        try {
//            Student student = new Student();
//            student.setStudentName("unitofwork");
//            student.setAge(20);
//            student.setBirthday(new Date());
//            student.setIdCard("132456");
//            student.setSex(1);
//            String insertSql = "insert into ws_student(student_name,age,cardid,birthday,sex) values(:studentName,:age,:cardId,:birthday,:sex)";
//            int result = dbContext.execute(unitOfWork.getDataSource(), insertSql, student);
//            unitOfWork.commit();
//            Assertions.assertTrue(result > 0);
//        } catch (Exception ex) {
//            unitOfWork.rollback();
//        }
//    }
//
//    @Test
//    @Order(24)
//    public void executeScalarGaussTest() {
//        String sql = "select student_name from ws_student where rownum<=1";
//
//        String studentName = dbContext.executeScalar(gauss, sql, null, String.class);
//        Assertions.assertTrue(StringUtils.isNotBlank(studentName));
//
//        String name = dbContext.executeScalar("gauss", sql, null, String.class);
//        Assertions.assertTrue(StringUtils.isNotBlank(studentName));
//    }
//
//    @Test
//    @Order(25)
//    public void queryGaussTest() {
//        String sql = "select * from ws_student";
//        List<Student> list = dbContext.query(gauss, sql, null, Student.class);
//
//        List<Student> list1 = dbContext.query("gauss", sql, null, Student.class);
//
//        Assertions.assertTrue(list.size() == list1.size() && list.size() > 0);
//
//        List<Map<String, Object>> maps = dbContext.query(gauss, sql, null);
//        List<Map<String, Object>> maps1 = dbContext.query("gauss", sql, null);
//        Assertions.assertTrue(maps1.size() == maps.size() && maps.size() > 0);
//
//    }
//
//    @Test
//    @Order(26)
//    public void updateGaussTest() {
//        String sql = "select * from ws_student  where rownum<=1";
//        Student student = dbContext.querySingle(gauss, sql, null, Student.class);
//
//        Student student1 = dbContext.querySingle("gauss", sql, null, Student.class);
//
//        Assertions.assertTrue(student != null && student1 != null);
//
//
//        student.setStudentName("updateTest");
//        int result = dbContext.execute(gauss, "update ws_student set student_name=:studentName where student_id=:studentId", student);
//        int result1 = dbContext.execute("gauss", "update ws_student set student_name=:studentName where student_id=:studentId", student);
//        Student student2 = dbContext.querySingle(gauss, "select * from ws_student where student_id=:studentId", student, Student.class);
//        Assertions.assertTrue(result == result1 && student2.getStudentName().equals("updateTest"));
//    }
//
//    @Test
//    @Order(27)
//    public void deleteGaussTest() {
//        String sql = "select * from ws_student where rownum<=1";
//        Student student = dbContext.querySingle(gauss, sql, null, Student.class);
//
//        int result = dbContext.execute(gauss, "delete from ws_student where student_id=:studentId", student);
//        Assertions.assertTrue(result > 0);
//    }
//
//    @Test
//    @Order(28)
//    public void deleteGaussTest1() {
//        String sql = "select * from ws_student where rownum<=2";
//        List<Student> student = dbContext.query(gauss, sql, null, Student.class);
//
//        int result = dbContext.execute(gauss, "delete from ws_student where student_id=:studentId", student);
//        Assertions.assertTrue(result > 0);
//    }
//
//    //Postgresql数据库测试
//
//    @Test
//    @Order(19)
//    public void insertPgTest() {
//        Student student = new Student();
//        student.setStudentName("feaf");
//        student.setAge(20);
//        student.setBirthday(new Date());
//        student.setIdCard("132456");
//        student.setSex(1);
//        String insertSql = "insert into ws_student(student_name,age,cardid,birthday,sex) values(:studentName,:age,:cardId,:birthday,:sex)";
//        int result = dbContext.execute("pg", insertSql, student);
//        Assertions.assertEquals(1L, result);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("studentName", "feagearga");
//        map.put("age", 20);
//        map.put("cardId", "4586132");
//        map.put("birthday", new Date());
//        map.put("sex", 0);
//        int result1 = dbContext.execute(pg, insertSql, map);
//        Assertions.assertEquals(1L, result1);
//    }
//
//    @Test
//    @Order(20)
//    public void insertObjectListPgTest() {
//        List<Student> list = new ArrayList<>();
//        Student student = new Student();
//        student.setStudentName("feaf");
//        student.setAge(20);
//        student.setBirthday(new Date());
//        student.setIdCard("132456");
//        student.setSex(1);
//        list.add(student);
//        Student student1 = new Student();
//        student1.setStudentName("feaf789fea");
//        student1.setAge(20);
//        student1.setBirthday(new Date());
//        student1.setIdCard("132456fea");
//        student1.setSex(1);
//        list.add(student1);
//        String insertSql = "insert into ws_student(student_name,age,cardid,birthday,sex) values(:studentName,:age,:cardId,:birthday,:sex)";
//        int result = dbContext.execute(pg, insertSql, list);
//        Assertions.assertEquals(2L, result);
//
//        List<Map<String, Object>> list1 = new ArrayList<>();
//        Map<String, Object> student2 = new HashMap<>();
//        student2.put("studentName", "feagearga");
//        student2.put("age", 20);
//        student2.put("cardId", "4586132");
//        student2.put("birthday", new Date());
//        student2.put("sex", 0);
//        list1.add(student2);
//        Map<String, Object> student3 = new HashMap<>();
//        student3.put("studentName", "studentmaplist");
//        student3.put("age", 20);
//        student3.put("cardId", "4586132");
//        student3.put("birthday", new Date());
//        student3.put("sex", 0);
//        list1.add(student3);
//        int result1 = dbContext.execute(pg, insertSql, list1);
//        Assertions.assertEquals(2L, result1);
//    }
//
//    @Test
//    @Order(21)
//    public void inertReturningPgTest() {
//        String insertSql = "INSERT INTO WS_STUDENT(STUDENT_NAME,AGE,CARDID,BIRTHDAY,SEX) values(:studentName,:age,:cardId,:birthday,:sex)";
//
//
//        Student student = new Student();
//        student.setStudentName("789456123");
//        student.setAge(20);
//        student.setBirthday(new Date());
//        student.setIdCard("132456");
//        student.setSex(1);
//        Number result = dbContext.executeReturning(pg, insertSql, student, returningOptions -> returningOptions.setKeyColumnName("student_id"));
//        Assertions.assertTrue(result.intValue() > 0);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("studentName", "feagearga");
//        map.put("age", 20);
//        map.put("cardId", "4586132");
//        map.put("birthday", new Date());
//        map.put("sex", 0);
//        Number result1 = dbContext.executeReturning(pg, insertSql, map, returningOptions -> returningOptions.setKeyColumnName("student_id"));
//        Assertions.assertTrue(result1.intValue() > 0);
//
//        Student student1 = new Student();
//        student1.setStudentName("789456123");
//        student1.setAge(20);
//        student1.setBirthday(new Date());
//        student1.setIdCard("132456");
//        student1.setSex(1);
//        Number result2 = dbContext.executeReturning("pg", insertSql, student1, returningOptions -> returningOptions.setKeyColumnName("student_id"));
//        Assertions.assertTrue(result2.intValue() > 0);
//
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("studentName", "feagearga");
//        map1.put("age", 20);
//        map1.put("cardId", "4586132");
//        map1.put("birthday", new Date());
//        map1.put("sex", 0);
//        Number result3 = dbContext.executeReturning("pg", insertSql, map1, returningOptions -> returningOptions.setKeyColumnName("student_id"));
//        Assertions.assertTrue(result3.intValue() > 0);
//    }
//
//    @Test
//    @Order(23)
//    public void unitOfWorkPgTest() {
//        UnitOfWork unitOfWork = dbContext.getUnitOfWork(pg);
//        unitOfWork.begin();
//        try {
//            Student student = new Student();
//            student.setStudentName("unitofwork");
//            student.setAge(20);
//            student.setBirthday(new Date());
//            student.setIdCard("132456");
//            student.setSex(1);
//            String insertSql = "insert into ws_student(student_name,age,cardid,birthday,sex) values(:studentName,:age,:cardId,:birthday,:sex)";
//            int result = dbContext.execute(unitOfWork.getDataSource(), insertSql, student);
//            unitOfWork.commit();
//            Assertions.assertTrue(result > 0);
//        } catch (Exception ex) {
//            unitOfWork.rollback();
//        }
//    }
//
//    @Test
//    @Order(24)
//    public void executeScalarPgTest() {
//        String sql = "select student_name from ws_student   limit 1";
//
//        String studentName = dbContext.executeScalar(pg, sql, null, String.class);
//        Assertions.assertTrue(StringUtils.isNotBlank(studentName));
//
//        String name = dbContext.executeScalar("pg", sql, null, String.class);
//        Assertions.assertTrue(StringUtils.isNotBlank(studentName));
//    }
//
//    @Test
//    @Order(25)
//    public void queryPgTest() {
//        String sql = "select * from ws_student";
//        List<Student> list = dbContext.query(pg, sql, null, Student.class);
//
//        List<Student> list1 = dbContext.query("pg", sql, null, Student.class);
//
//        Assertions.assertTrue(list.size() == list1.size() && list.size() > 0);
//
//        List<Map<String, Object>> maps = dbContext.query(pg, sql, null);
//        List<Map<String, Object>> maps1 = dbContext.query("pg", sql, null);
//        Assertions.assertTrue(maps1.size() == maps.size() && maps.size() > 0);
//
//    }
//
//    @Test
//    @Order(26)
//    public void updatePgTest() {
//        String sql = "select * from ws_student  limit 1";
//        Student student = dbContext.querySingle(pg, sql, null, Student.class);
//
//        Student student1 = dbContext.querySingle("pg", sql, null, Student.class);
//
//        Assertions.assertTrue(student != null && student1 != null);
//
//
//        student.setStudentName("updateTest");
//        int result = dbContext.execute(pg, "update ws_student set student_name=:studentName where student_id=:studentId", student);
//        int result1 = dbContext.execute("pg", "update ws_student set student_name=:studentName where student_id=:studentId", student);
//        Student student2 = dbContext.querySingle(pg, "select * from ws_student where student_id=:studentId", student, Student.class);
//        Assertions.assertTrue(result == result1 && student2.getStudentName().equals("updateTest"));
//    }
//
//    @Test
//    @Order(27)
//    public void deletePgTest() {
//        String sql = "select * from ws_student  limit 1";
//        Student student = dbContext.querySingle(pg, sql, null, Student.class);
//
//        int result = dbContext.execute(pg, "delete from ws_student where student_id=:studentId", student);
//        Assertions.assertTrue(result > 0);
//    }
//
//    @Test
//    @Order(28)
//    public void deletePgTest1() {
//        String sql = "select * from ws_student limit 2";
//        List<Student> student = dbContext.query(pg, sql, null, Student.class);
//
//        int result = dbContext.execute(pg, "delete from ws_student where student_id=:studentId", student);
//        Assertions.assertTrue(result > 0);
//    }
}

