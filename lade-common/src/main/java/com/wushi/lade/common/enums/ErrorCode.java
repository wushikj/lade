package com.wushi.lade.common.enums;

import com.wushi.lade.common.interfaces.BaseErrorCode;

/**
 * 错误码枚举类
 *
 * @author wushi
 * @date 2020/1/7 8:54
 */
public enum ErrorCode implements BaseErrorCode {

    /**
     * 未知的错误
     */
    UnknownError("未知的错误", 99999),

    // Env------------------------------------------------

    Env_JdkVersionIncorrect("jdk版本不正确", 10001),
    Env_CpuPlatformNotSupported("CPU架构不支持", 10002),

    // Config------------------------------------------------

    Config_InvalidConfiguration("无效的配置", 13001),
    Config_InvalidConfigCenterEndpoint("无效的配置中心端点", 13002),
    Config_InvalidAppConfigDirectory("无效的应用程序配置目录", 13003),
    Config_InvalidAppScriptDirectory("无效的查询脚本目录", 13004),

    // Db------------------------------------------------

    Db_ConnectionConfigError("数据连接配置错误", 14000),
    Db_ConnectionStringNotFound("未找到指定的数据库连接串", 14001),
    Db_NotSupoortOperation("不支持的操作", 14002),
    Db_ConnectFailure("数据库连接失败", 14003),
    Db_ConnectTimeout("数据库连接超时", 14004),
    Db_InvalidDataProvider("无效的数据提供器", 14005),
    Db_InvalidParameter("无效的参数", 14006),
    Db_SqlSyntaxError("Sql语法错误", 14007),
    Db_SqlExecuteError("Sql语句执行错误", 14008),
    Db_SqlParseError("Sql语句解析错误", 14009),
    Db_DataProviderNotFound("数据提供器未找到，请确认是否已安装", 14015),
    Db_BatcherProviderNotFound("批量插入提供器未找到，请确认是已注册到DI容器", 14016),
    Db_ReturningNotFound("Returning处理器未找到，请确认是已注册到DI容器", 14017),
    Db_ConnectionIsNull("数据库连接对象为空", 14020),

    // Http------------------------------------------------

    Http_NotAllowedParameter("不合法的参数", 16001),
    Http_RequiredParameter("必要的参数", 16002),
    Http_NotSupportFileType("不支持的的文件类型", 16003),
    Http_RemoteHostCannotConnect("无法连接到远程服务器", 16004),
    Http_NotAllowedIP("不允许的IP地址", 16005),
    Http_RequiredHeaderParameter("缺少必要的Header参数", 16006),
    Http_RequiredParameterBind("请求参数绑定失败", 16007),
    Http_RequiredCheck("请求参数校验失败", 16008),
    Http_RequiredNotRead("消息不能被读取", 16009),
    Http_BadRequest("请求无效", 16400),
    Http_NotFound("请求的资源未找到", 16404),
    Http_MethodNotAllowed("无效的Http请求方法", 16405),
    Http_Unauthorized("未经授权的", 16401),
    Http_Forbidden("请求被禁止", 16403),
    Http_RequestEntityTooLarge("请求的实体过大", 16413),
    Http_RequestUriTooLong("请求的uri地址太长", 16414),
    Http_UnsupportedMediaType("不支持的媒体类型", 16415),
    Http_TooManyRequests("请求过多", 16429),
    Http_InternalServerError("内部服务器错误", 16500),
    Http_NotImplemented("未实现", 16501),
    Http_BadGateway("网关错误", 16502),
    Http_ServiceUnavailable("服务不可用", 16503),
    Http_GatewayTimeout("网关超时", 16504),

    // File------------------------------------------------

    File_FileOrDirectoryNotFound("文件或目录未找到", 17001),
    File_UploadFailure("文件上传失败", 17002),
    File_UnsupportFileFormat("不支持的文件格式", 17003),
    File_FileOrDirectoryAlreadyExists("同名的文件或目录已存在", 17004),
    File_FormatConvertError("文件格式转换错误", 17005),
    File_SizeTooLarge("文件过大", 17006),


    // 序列化------------------------------------------------

    Json_SerializationFailure("json序列化失败", 18000),
    Json_DeserializationFailure("json反序列化失败", 18001),
    Xml_SerializationFailure("xml序列化失败", 18002),
    Xml_DeserializationFailure("xml反序列化失败", 18003),
    Binary_SerializationFailure("二进制序列化失败", 18004),
    Binary_DeserializationFailure("二进制反序列化失败", 18005),

    //参数验证-----------------------------------------------------
    MethodArgument_NotValid("参数验证未通过", 19000),

    // API签名认证------------------------------------------------
    Sign_OnlyJsonSupport("ContentType目前只支持application/json格式", 23415),
    Sign_NotAllowedParameter("非法的签名参数", 23002),
    Sign_BodyDigestError("Body摘要不一致", 23004),
    Sign_SignatureError("签名认证失败", 23010),
    Sign_SignatureTimestampExpired("签名时间戳已过期", 23011),
    Sign_NonceDuplicated("签名请求的随机数重复", 23012),
    Sign_X_Lade_WebApi_Timestamp_Header_Required("X-Lade-WebApi-Timestamp请求头不能为空", 23013),
    Sign_X_Lade_WebApi_Nonce_Header_Required("X-Lade-WebApi-Nonce请求头不能为空", 23014),
    Sign_X_Lade_WebApi_Signature_Header_Required("X-Lade-WebApi-Signature请求头不能为空", 23015),
    Sign_X_Lade_WebApi_Content_MD5_Header_Required("X-Lade-WebApi-Content-MD5请求头不能为空", 23016),

    Service_NonRegister("服务未注册", 24001);


    /**
     * 构造方法
     */
    ErrorCode(String errorText,
              int errorCode) {
        this.errorText = errorText;
        this.errorCode = errorCode;
    }

    /**
     * 获取描述
     */
    public static String getText(int index) {
        for (ErrorCode c : ErrorCode.values()) {
            if (c.getErrorCode() == index) {
                return c.errorText;
            }
        }
        return null;
    }

    /**
     * 错误编码描述信息
     */
    private String errorText;
    /**
     * 错误编码
     */
    private int errorCode;

    /**
     * 获取错误编码描述信息
     *
     * @return {String}
     */
    @Override
    public String getErrorText() {
        return this.errorText;
    }

    /**
     * 设置错误编码描述信息
     *
     * @param errorCodeDescription 错误编码信息
     */
    public void setErrorText(String errorCodeDescription) {
        this.errorText = errorCodeDescription;
    }

    /**
     * 获取错误编码
     *
     * @return {int}
     */
    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    /**
     * 设置错误编码
     *
     * @param errorCode 错误编码
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
