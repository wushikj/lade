package com.wushi.lade.dao.plugins.permission;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.List;

/**
 * @author wushi
 * @date 2020/9/28 11:56
 */
public interface DataFilterHandler {

    /**
     * 获取租户 ID 值表达式，支持多个 ID 条件查询
     * <p>
     * 支持自定义表达式，比如：tenant_id in (1,2) @since 2019-8-2
     *
     * @param where 参数 true 表示为 where 条件 false 表示为 insert 或者 select 条件
     * @return 租户 ID 值表达式
     */
    Expression getDataFilter(boolean where);

    /**
     * 获取租户字段名
     *
     * @return 租户字段名
     */
    String getDataFilterColumn();

    /**
     * 根据表名判断是否进行过滤
     *
     * @param table
     * @return 是否进行过滤, true:表示忽略，false:需要解析多租户字段
     */
    boolean doTableFilter(Table table, List<SelectItem> selectItems);
}
