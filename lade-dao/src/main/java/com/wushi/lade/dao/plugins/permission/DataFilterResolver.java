package com.wushi.lade.dao.plugins.permission;


import com.wushi.lade.dao.entity.DataFilterCondition;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.List;

/**
 * @author wushi
 * @date 2020/9/29 14:48
 */
public interface DataFilterResolver {

    DataFilterCondition doTableFilter(Table table, List<SelectItem> selectItems);
}
