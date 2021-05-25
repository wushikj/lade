package com.wushi.lade.tests.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户通讯表
 *
 * @author wushi
 * @date 2020-01-07
 */
public class SysUserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "USER_ID",type = IdType.ASSIGN_UUID)
    private String userId;

    @TableField("USER_NAME")
    private String userName;

    @TableField("USER_PWD")
    private String userPwd;

    @TableField("USER_STATUS")
    private Integer userStatus;

    @TableField("UNLOCK_TIME")
    private LocalDateTime unlockTime;

    @TableField("ERROR_COUNT")
    private Integer errorCount;

    @TableField("IS_DEVELOPER")
    private Integer isDeveloper;

    @TableField("CLIENT_ID")
    private String clientId;

    @TableField("IS_MULTIPLE_LOGIN")
    private Integer isMultipleLogin;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("REFUSAL_REASON")
    private String refusalReason;

    @TableField("CREATE_ID")
    private String createId;

    @TableField("IS_APP")
    private Integer isApp;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public LocalDateTime getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(LocalDateTime unlockTime) {
        this.unlockTime = unlockTime;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getIsDeveloper() {
        return isDeveloper;
    }

    public void setIsDeveloper(Integer isDeveloper) {
        this.isDeveloper = isDeveloper;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getIsMultipleLogin() {
        return isMultipleLogin;
    }

    public void setIsMultipleLogin(Integer isMultipleLogin) {
        this.isMultipleLogin = isMultipleLogin;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getRefusalReason() {
        return refusalReason;
    }

    public void setRefusalReason(String refusalReason) {
        this.refusalReason = refusalReason;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Integer getIsApp() {
        return isApp;
    }

    public void setIsApp(Integer isApp) {
        this.isApp = isApp;
    }
}
