package com.wushi.lade.tests.entity.gauss;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author wushi
 * @date 2020/1/20 16:22
 */

@TableName("Test")
public class TestByGauss implements Serializable {

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;
    @TableField
    private String name;
    @TableField
    private String telephone;
    @TableField
    private String sex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
