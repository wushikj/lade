package com.wushi.lade.tests.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author wushi
 * @date 2020-01-07
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "USER_ID",type = IdType.ASSIGN_UUID)
    private String userId;

    @TableField("REAL_NAME")
    private String realName;

    @TableField("TELEPHONE")
    private String telephone;

    @TableField("ID_CARD")
    private String idCard;

    @TableField("SEX")
    private String sex;

    @TableField("EMAIL")
    private String email;

    @TableField("ADDRESS")
    private String address;

    @TableField("REMARK")
    private String remark;

    @TableField("SORT_ID")
    private Integer sortId;

    @TableField("PIN_YIN")
    private String pinYin;

    @TableField("HEAD_PORTRAIL")
    private String headPortrail;

    @TableField("IS_HIDE_PHONE")
    private Integer isHidePhone;

    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;

    @TableField(value = "UPDATE_TIME")
    private LocalDateTime updateTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getHeadPortrail() {
        return headPortrail;
    }

    public void setHeadPortrail(String headPortrail) {
        this.headPortrail = headPortrail;
    }

    public Integer getIsHidePhone() {
        return isHidePhone;
    }

    public void setIsHidePhone(Integer isHidePhone) {
        this.isHidePhone = isHidePhone;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
