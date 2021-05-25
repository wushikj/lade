package com.wushi.lade.dao.interfaces.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.wushi.lade.common.utils.MapKeyNamingUtils;
import com.wushi.lade.dao.interfaces.DbContext;
import com.wushi.lade.dao.interfaces.UnitOfWork;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wushi
 * @date 2020/5/29 15:17
 */
@Service
public class DbContextImpl implements DbContext {

    private Map<String, Configuration> configurationMap;

    public DbContextImpl() {
        configurationMap = new HashMap<>();
    }

    @Resource
    private DynamicDataSourceProvider dynamicRoutingDataSource;

    @Override
    public DruidDataSource getDataSource(String url, String userName, String password, String driverClassName) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        String name = String.valueOf(String.format("%s.%s.%s", url, userName, password).hashCode());
        dataSource.setName(name);
        return dataSource;
    }

    @Override
    public <T> List<T> query(DruidDataSource dataSource, String sql, Object param, Class<T> clazz) throws SQLException {
        Pair<SqlSession, String> pair = getSqlSessionAndMappedId(dataSource, sql, param, clazz, SqlCommandType.SELECT);
        SqlSession sqlSession = pair.getLeft();
        String mappedStatementId = pair.getRight();
        List<T> list = sqlSession.selectList(mappedStatementId, param);
        return list;
    }

    @Override
    public <T> List<T> query(String dataSourceName, String sql, Object param, Class<T> clazz) throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) dynamicRoutingDataSource.loadDataSources().get(dataSourceName);
        return query(dataSource, sql, param, clazz);
    }

    @Override
    public List<Map<String, Object>> query(DruidDataSource dataSource, String sql, Object param) throws SQLException {
        return query(dataSource, sql, param, PropertyNamingStrategy.LOWER_CAMEL_CASE);
    }

    @Override
    public List<Map<String, Object>> query(String dataSourceName, String sql, Object param) throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) dynamicRoutingDataSource.loadDataSources().get(dataSourceName);
        return query(dataSource, sql, param, PropertyNamingStrategy.LOWER_CAMEL_CASE);
    }

    @Override
    public List<Map<String, Object>> query(DruidDataSource dataSource, String sql, Object param, PropertyNamingStrategy propertyNamingStrategy) throws SQLException {
        Pair<SqlSession, String> pair = getSqlSessionAndMappedId(dataSource, sql, param, Map.class, SqlCommandType.SELECT);
        SqlSession sqlSession = pair.getLeft();
        String mappedStatementId = pair.getRight();
        List<Map<String, Object>> list = sqlSession.selectList(mappedStatementId, param);
        return MapKeyNamingUtils.convertMapList(list, propertyNamingStrategy);
    }

    @Override
    public List<Map<String, Object>> query(String dataSourceName, String sql, Object param, PropertyNamingStrategy propertyNamingStrategy) throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) dynamicRoutingDataSource.loadDataSources().get(dataSourceName);
        return query(dataSource, sql, param, propertyNamingStrategy);
    }

    @Override
    public <T> T querySingle(DruidDataSource dataSource, String sql, Object param, Class<T> clazz) throws SQLException {
        Pair<SqlSession, String> pair = getSqlSessionAndMappedId(dataSource, sql, param, clazz, SqlCommandType.SELECT);
        SqlSession sqlSession = pair.getLeft();
        String mappedStatementId = pair.getRight();
        T result = sqlSession.selectOne(mappedStatementId, param);
        return result;
    }

    @Override
    public <T> T querySingle(String dataSourceName, String sql, Object param, Class<T> clazz) throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) dynamicRoutingDataSource.loadDataSources().get(dataSourceName);
        return querySingle(dataSource, sql, param, clazz);
    }

    @Override
    public Map<String, Object> querySingle(DruidDataSource dataSource, String sql, Object param) throws SQLException {
        return querySingle(dataSource, sql, param, PropertyNamingStrategy.LOWER_CAMEL_CASE);
    }

    @Override
    public Map<String, Object> querySingle(String dataSourceName, String sql, Object param) throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) dynamicRoutingDataSource.loadDataSources().get(dataSourceName);
        return querySingle(dataSource, sql, param, PropertyNamingStrategy.LOWER_CAMEL_CASE);
    }

    @Override
    public Map<String, Object> querySingle(DruidDataSource dataSource, String sql, Object param, PropertyNamingStrategy propertyNamingStrategy) throws SQLException {
        Map<String, Object> map = querySingle(dataSource, sql, param, Map.class);
        return MapKeyNamingUtils.convertMap(map, propertyNamingStrategy);
    }

    @Override
    public Map<String, Object> querySingle(String dataSourceName, String sql, Object param, PropertyNamingStrategy propertyNamingStrategy) throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) dynamicRoutingDataSource.loadDataSources().get(dataSourceName);
        return querySingle(dataSource, sql, param, propertyNamingStrategy);
    }

    @Override
    public int insert(DruidDataSource dataSource, String sql, Object param) throws SQLException {
        return insert(dataSource, sql, param, null, null);
    }

    @Override
    public int insert(String dataSourceName, String sql, Object param) throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) dynamicRoutingDataSource.loadDataSources().get(dataSourceName);
        return insert(dataSource, sql, param);
    }

    @Override
    public int insert(DruidDataSource dataSource, String sql, Object param, String keyProperty, String keyColumn) throws SQLException {
        return execute(dataSource, sql, param, SqlCommandType.INSERT, keyProperty, keyColumn);
    }

    @Override
    public int insert(String dataSourceName, String sql, Object param, String keyProperty, String keyColumn) throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) dynamicRoutingDataSource.loadDataSources().get(dataSourceName);
        return insert(dataSource, sql, param, keyProperty, keyColumn);
    }

    @Override
    public int update(DruidDataSource dataSource, String sql, Object param) throws SQLException {
        return execute(dataSource, sql, param, SqlCommandType.UPDATE, null, null);
    }

    @Override
    public int update(String dataSourceName, String sql, Object param) throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) dynamicRoutingDataSource.loadDataSources().get(dataSourceName);
        return execute(dataSource, sql, param, SqlCommandType.UPDATE, null, null);
    }

    @Override
    public int delete(DruidDataSource dataSource, String sql, Object param) throws SQLException {
        return execute(dataSource, sql, param, SqlCommandType.DELETE, null, null);
    }

    @Override
    public int delete(String dataSourceName, String sql, Object param) throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) dynamicRoutingDataSource.loadDataSources().get(dataSourceName);
        return execute(dataSource, sql, param, SqlCommandType.DELETE, null, null);
    }

    @Override
    public <T> T executeScalar(DruidDataSource dataSource, String sql, Object param, Class<T> clazz) throws SQLException {
        Map<String, Object> map = querySingle(dataSource, sql, param);
        Object val = map.values().stream().findFirst().get();
        return clazz.cast(val);
    }

    @Override
    public <T> T executeScalar(String dataSourceName, String sql, Object param, Class<T> clazz) throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) dynamicRoutingDataSource.loadDataSources().get(dataSourceName);
        return executeScalar(dataSource, sql, param, clazz);
    }

    @Override
    public UnitOfWork getUnitOfWork(DruidDataSource dataSource) {
        return new UnitOfWorkImpl(dataSource);
    }

    @Override
    public UnitOfWork getUnitOfWork(String dataSourceName) {
        DruidDataSource dataSource = (DruidDataSource) dynamicRoutingDataSource.loadDataSources().get(dataSourceName);
        return getUnitOfWork(dataSource);
    }

    private int execute(DruidDataSource dataSource, String sql, Object param, SqlCommandType sqlCommandType, String keyProperty, String keyColumn) throws SQLException {
        SqlSession sqlSession = null;
        if (param instanceof List) {
            List list = (List) param;
            Object first = list.get(0);
            Pair<SqlSession, String> pair = getSqlSessionAndMappedId(dataSource, sql, first, int.class, sqlCommandType, keyProperty, keyColumn);
            sqlSession = pair.getLeft();
            String mappedStatementId = pair.getRight();
            int affectedRows = 0;
            for (Object obj : list) {
                int result = sqlSession.insert(mappedStatementId, obj);
                affectedRows += result;
            }
            return affectedRows;
        } else {
            Pair<SqlSession, String> pair = getSqlSessionAndMappedId(dataSource, sql, param, int.class, sqlCommandType, keyProperty, keyColumn);
            sqlSession = pair.getLeft();
            String mappedStatementId = pair.getRight();
            int affectedRows = sqlSession.insert(mappedStatementId, param);
            return affectedRows;
        }
    }

    /**
     * 根据sql语句、返回值类型、参数类型创建Configuration、MappedStatement，并返回SqlSession
     *
     * @param dataSource
     * @param sql
     * @param param
     * @param clazz
     * @param sqlCommandType
     * @return org.apache.commons.lang3.tuple.Pair<org.apache.ibatis.session.SqlSession, java.lang.String>
     * @author wushi
     * @date 2020/5/29 16:55
     */
    private synchronized <T> Pair<SqlSession, String> getSqlSessionAndMappedId(DruidDataSource dataSource, String sql, Object param, Class<T> clazz, SqlCommandType sqlCommandType) throws SQLException {
        return getSqlSessionAndMappedId(dataSource, sql, param, clazz, sqlCommandType, null, null);
    }

    /**
     * 根据sql语句、返回值类型、参数类型创建Configuration、MappedStatement，并返回SqlSession
     *
     * @param dataSource
     * @param sql
     * @param param
     * @param clazz
     * @param sqlCommandType
     * @return org.apache.commons.lang3.tuple.Pair<org.apache.ibatis.session.SqlSession, java.lang.String>
     * @author wushi
     * @date 2020/5/29 16:55
     */
    private synchronized <T> Pair<SqlSession, String> getSqlSessionAndMappedId(DruidDataSource dataSource, String sql, Object param, Class<T> clazz, SqlCommandType sqlCommandType, String keyProperty, String keyColumn) throws SQLException {
        Configuration configuration = null;
        //判断Configuration是否存在
        if (!configurationMap.containsKey(dataSource.getName())) {
            TransactionFactory transactionFactory = new SpringManagedTransactionFactory();
            Environment env = new Environment(dataSource.getName(), transactionFactory, dataSource);
            configuration = new Configuration(env);
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setUseGeneratedKeys(true);
            this.configurationMap.put(dataSource.getName(), configuration);
        } else {
            configuration = configurationMap.get(dataSource.getName());
        }

        String resultTypeName = clazz != null ? clazz.getTypeName() : "";
        String paramTypeName = param != null ? param.getClass().getTypeName() : "";
        String mappedStatementId = String.valueOf(String.format("%s.%s.%s.%s.%s.%s", sql, dataSource.getName(), resultTypeName, paramTypeName, keyProperty, keyColumn).hashCode());

        //判断mappedStatementId是否存在，没有创建MappedStatement
        if (!configuration.hasStatement(mappedStatementId)) {
            //创建SqlSource
            SqlSource sqlSource = null;
            if (param != null) {
                //设置参数类型
                LanguageDriver languageDriver = configuration.getDefaultScriptingLanguageInstance();
                sqlSource = languageDriver.createSqlSource(configuration, sql, param.getClass());
            } else {
                sqlSource = new StaticSqlSource(configuration, sql);
            }
            //设置返回类型
            List<ResultMap> resultMaps = new ArrayList<>();
            ResultMap resultMap = new ResultMap.Builder(configuration, "defaultResultMap", clazz, new ArrayList<ResultMapping>(0)).build();
            resultMaps.add(resultMap);
            //创建MappedStatement
            MappedStatement.Builder builder = new MappedStatement.Builder(configuration, mappedStatementId, sqlSource, sqlCommandType).resultMaps(resultMaps);
            if (StringUtils.isNotEmpty(keyProperty) && StringUtils.isNotEmpty(keyColumn)) {
                builder.keyProperty(keyProperty);
                builder.keyColumn(keyColumn);
            }
            MappedStatement mappedStatement = builder.build();
            configuration.addMappedStatement(mappedStatement);
        }

        //创建SqlSession并返回
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        //不能直接使用SqlSession，否则需要手动关闭sqlsession，手动关闭会导致工作单元无法使用
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sessionFactory);
        return Pair.of(sqlSessionTemplate, mappedStatementId);
    }
}
