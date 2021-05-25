## Lade基础开发平台

基于Spring Boot的接口开发框架， 搭建了统一的、功能完备、具有丰富扩展接口的基础设施层，通过统一标准和接入规范，实现业务集成模块化和数据存储通用化，并有利于系统升级、功能的维护与延伸。

## 技术文档



## 功能

- 统一的接口返回结构
- 统一的全局异常处理
- 统一的缓存API
- 多租户
- 跨域控制
- swagger文档支持
- 恶意文件拦截
- IP访问限制
- 参数签名
- 请求链路跟踪
- Elasticsearch操作
- 加密工具类


## 技术架构

- 基础框架：Spring Boot 2.4.4.RELEASE
- 持久层框架：Mybatis-plus 3.4.2
- 数据库连接池：Druid 1.2.5
- 缓存框架：redis/ehcache
- 日志打印：logback
- 其他：jackson、Swagger-ui， lombok（简化代码）等。