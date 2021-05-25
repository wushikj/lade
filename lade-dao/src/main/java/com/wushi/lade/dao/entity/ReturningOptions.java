package com.wushi.lade.dao.entity;

/**
 * @author wushi
 * @date 2020/3/30 14:37
 * @description
 */
public class ReturningOptions {

    /**
     * 主键名
     **/
    private String keyColumnName;

    /**
     * 序列名
     **/
    private String sequenceName;

    public String getKeyColumnName() {
        return keyColumnName;
    }

    public void setKeyColumnName(String keyColumnName) {
        this.keyColumnName = keyColumnName;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }
}
