package com.wushi.lade.dao;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.fasterxml.jackson.databind.JsonNode;
import com.wushi.lade.LadeTestsApplication;
import com.wushi.lade.tests.mapper.sqlserver.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wushi
 * @date 2020/2/28 10:18
 * @description
 */
@SpringBootTest(classes = LadeTestsApplication.class)
public class PageTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PaginationInterceptor paging;

    @Test
    public void PageTest() {
        IPage page = new Page(1, 10, true);
        IPage<JsonNode> list = userMapper.selectUsers(page);
        System.out.println(list.getRecords());
    }

    @Test
    public void PageTest1() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = new ClassPathResource(resource).getInputStream();
        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(inputStream);
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
//        MybatisMapperMethod mapperMethod = cachedMapperMethod(method);
        List<JsonNode> list = null;
        IPage page = new Page(1, 10, true);
        Map<String, Object> param = new HashMap<>();
        param.put("id", 2);
//        MappedStatement ms = sqlSessionTemplate.getConfiguration().getMappedStatement("User.selectUsers");
//        JdbcTemplate jdbcTemplate = new JdbcTemplate();
//        DriverManagerDataSource dataSource=new DriverManagerDataSource();
//        dataSource.setDriverClassName(databaseMetaData.getDriverName());
//        dataSource.setUsername(databaseMetaData.getUserName());
//        dataSource.setPassword(databaseMetaData.ge);
//        BoundSql boundSql = ms.getBoundSql(param);
        JsqlParserCountOptimize sqlParse = new JsqlParserCountOptimize();
//        SqlInfo countSqlInfo = SqlParserUtils.getOptimizeCountSql(true, sqlParse, boundSql.getSql());
        RowBounds rowBounds = new RowBounds(1, 10);
        list = sqlSessionTemplate.selectList("User.selectUsers", param, rowBounds);

    }
}
