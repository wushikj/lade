package com.wushi.lade.tests.entity.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;

/**
 * @author wushi
 * @date 2020/2/3 17:33
 */
@TableName("PERSON.ADDRESS")
public class Address implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
