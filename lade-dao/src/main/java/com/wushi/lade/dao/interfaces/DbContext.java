package com.wushi.lade.dao.interfaces;

import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author wushi
 * @date 2020/3/24 10:37
 * @description
 */
public interface DbContext {

    UnitOfWork unitOfWork = null;

    /**
     * 获取数据源
     *
     * @param url
     * @param userName
     * @param password
     * @param driverClassName
     * @return javax.sql.DruidDataSource
     * @author wushi
     * @date 2020/3/25 17:27
     * @description
     */
    DruidDataSource getDataSource(String url, String userName, String password, String driverClassName);

    /**
     * 通过sql语句查询，返回多条数据
     *
     * @param dataSource 数据源
     * @param sql        查询语句
     * @param param      参数
     * @param clazz      返回类型
     * @return java.util.List<T>
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    <T> List<T> query(DruidDataSource dataSource, String sql, Object param, Class<T> clazz) throws SQLException;

    /**
     * 通过sql语句查询，返回多条数据
     *
     * @param dataSourceName 配置文件里配置的数据源名称
     * @param sql            查询语句
     * @param param          参数
     * @param clazz          返回类型
     * @return java.util.List<T>
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    <T> List<T> query(String dataSourceName, String sql, Object param, Class<T> clazz) throws SQLException;

    /**
     * 通过sql语句查询，返回多条Map类型数据，默认为小写驼峰
     *
     * @param dataSource 数据源
     * @param sql        查询语句
     * @param param      参数
     * @return java.util.List<T>O
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    List<Map<String, Object>> query(DruidDataSource dataSource, String sql, Object param) throws SQLException;

    /**
     * 通过sql语句查询，返回多条Map类型数据，默认为小写驼峰
     *
     * @param dataSourceName 配置文件里配置的数据源名称
     * @param sql            查询语句
     * @param param          参数
     * @return java.util.List<T>O
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    List<Map<String, Object>> query(String dataSourceName, String sql, Object param) throws SQLException;

    /**
     * 通过sql语句查询，返回多条Map类型数据
     *
     * @param propertyNamingStrategy key转换策略
     * @param dataSource             数据源
     * @param sql                    查询语句
     * @param param                  参数
     * @return java.util.List<T>
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    List<Map<String, Object>> query(DruidDataSource dataSource, String sql, Object param, PropertyNamingStrategy propertyNamingStrategy) throws SQLException;

    /**
     * 通过sql语句查询，返回多条Map类型数据
     *
     * @param propertyNamingStrategy key转换策略
     * @param dataSourceName         配置文件里配置的数据源名称
     * @param sql                    查询语句
     * @param param                  参数
     * @return java.util.List<T>
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    List<Map<String, Object>> query(String dataSourceName, String sql, Object param, PropertyNamingStrategy propertyNamingStrategy) throws SQLException;

    /**
     * 通过sql语句查询单条数据
     *
     * @param clazz      返回类型
     * @param dataSource 数据源
     * @param sql        查询语句
     * @param param      参数
     * @return java.util.List<T>
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    <T> T querySingle(DruidDataSource dataSource, String sql, Object param, Class<T> clazz) throws SQLException;

    /**
     * 通过sql语句查询单条数据
     *
     * @param clazz          返回类型
     * @param dataSourceName 配置文件里配置的数据源名称
     * @param sql            查询语句
     * @param param          参数
     * @return java.util.List<T>
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    <T> T querySingle(String dataSourceName, String sql, Object param, Class<T> clazz) throws SQLException;

    /**
     * 通过sql语句查询单条Map数据，默认为小定驼峰
     *
     * @param dataSource 数据源
     * @param sql        查询语句
     * @param param      参数
     * @return java.util.List<T>
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    Map<String, Object> querySingle(DruidDataSource dataSource, String sql, Object param) throws SQLException;

    /**
     * 通过sql语句查询单条Map数据，默认为小定驼峰
     *
     * @param dataSourceName 配置文件里配置的数据源名称
     * @param sql            查询语句
     * @param param          参数
     * @return java.util.List<T>
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    Map<String, Object> querySingle(String dataSourceName, String sql, Object param) throws SQLException;

    /**
     * 通过sql语句查询单条Map数据
     *
     * @param propertyNamingStrategy key转换策略
     * @param dataSource             数据源
     * @param sql                    查询语句
     * @param param                  参数
     * @return java.util.List<T>
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    Map<String, Object> querySingle(DruidDataSource dataSource, String sql, Object param, PropertyNamingStrategy propertyNamingStrategy) throws SQLException;

    /**
     * 通过sql语句查询单条Map数据
     *
     * @param propertyNamingStrategy key转换策略
     * @param dataSourceName         配置文件里配置的数据源名称
     * @param sql                    查询语句
     * @param param                  参数
     * @return java.util.List<T>
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    Map<String, Object> querySingle(String dataSourceName, String sql, Object param, PropertyNamingStrategy propertyNamingStrategy) throws SQLException;

    /**
     * 添加
     *
     * @param dataSource 数据源
     * @param sql        添加语句
     * @param param      参数
     * @return java.util.List<T>
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    int insert(DruidDataSource dataSource, String sql, Object param) throws SQLException;

    /**
     * 添加
     *
     * @param dataSourceName 配置文件里配置的数据源名称
     * @param sql            添加语句
     * @param param          参数
     * @return java.util.List<T>
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    int insert(String dataSourceName, String sql, Object param) throws SQLException;

    /**
     * 添加后返回自增主键，支持多条
     *
     * @param keyProperty 主键名
     * @param dataSource  数据源
     * @param sql         添加语句
     * @param param       参数
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    int insert(DruidDataSource dataSource, String sql, Object param, String keyProperty,String keyColumn) throws SQLException;

    /**
     * 添加后返回自增主键，支持多条
     *
     * @param keyProperty    主键名
     * @param dataSourceName 配置文件里配置的数据源名称
     * @param sql            添加语句
     * @param param          参数
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    int insert(String dataSourceName, String sql, Object param, String keyProperty,String keyColumn) throws SQLException;

    /**
     * 修改操作，支持多条
     *
     * @param dataSource 数据源
     * @param sql        添加语句
     * @param param      参数
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    int update(DruidDataSource dataSource, String sql, Object param) throws SQLException;


    /**
     * 修改操作，支持多条
     *
     * @param dataSourceName 配置文件里配置的数据源名称
     * @param sql            添加语句
     * @param param          参数
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    int update(String dataSourceName, String sql, Object param) throws SQLException;

    /**
     * 删除操作，支持多条
     *
     * @param dataSource 数据源
     * @param sql        添加语句
     * @param param      参数
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    int delete(DruidDataSource dataSource, String sql, Object param) throws SQLException;


    /**
     * 删除操作，支持多条
     *
     * @param dataSourceName 配置文件里配置的数据源名称
     * @param sql            添加语句
     * @param param          参数
     * @author wushi
     * @date 2020/3/25 14:00
     * @description
     */
    int delete(String dataSourceName, String sql, Object param) throws SQLException;

    /**
     * 返回单个字段值
     *
     * @param dataSource 数据源
     * @param sql        查询语句
     * @param param      参数
     * @return T
     * @author wushi
     * @date 2020/3/25 15:33
     * @description
     */
    <T> T executeScalar(DruidDataSource dataSource, String sql, Object param, Class<T> clazz) throws SQLException;

    /**
     * 返回单个字段值
     *
     * @param dataSourceName 配置文件里配置的数据源名称
     * @param sql            查询语句
     * @param param          参数
     * @return T
     * @author wushi
     * @date 2020/3/25 15:33
     * @description
     */
    <T> T executeScalar(String dataSourceName, String sql, Object param, Class<T> clazz) throws SQLException;

    /**
     * 获取工作单元
     *
     * @param dataSource 数据源
     * @return UnitOfWork
     * @author wushi
     * @date 2020/3/25 16:02
     * @description
     */
    UnitOfWork getUnitOfWork(DruidDataSource dataSource);

    /**
     * 获取工作单元
     *
     * @param dataSourceName 配置文件里配置的数据源名称
     * @return UnitOfWork
     * @author wushi
     * @date 2020/3/25 16:02
     * @description
     */
    UnitOfWork getUnitOfWork(String dataSourceName);
}
