package com.wushi.lade.tests.entity.oracle;

import com.baomidou.mybatisplus.annotation.*;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author wushi
 * @since 2020-01-08
 */
@KeySequence(value = "SEQ_Test_Auto_Increment")
@TableName("Test_Auto_Increment")
public class TestBatchInsertByOracle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private Integer id;

    @TableField("NAME")
    private String name;

    @TableField("ADD_TIME")
    private LocalDateTime addTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }
}
