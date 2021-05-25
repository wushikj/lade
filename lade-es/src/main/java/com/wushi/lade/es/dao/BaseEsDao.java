package com.wushi.lade.es.dao;

import com.wushi.lade.es.annotations.BaseDocument;

import java.util.List;

/**
 * @author wushi
 * @date 2020-07-31
 */
public interface BaseEsDao<T extends BaseDocument> {
    /**
     * 创建文档
     *
     * @param doc
     * @return
     */
    String createDocument(T doc);

    /**
     * 批量创建文档
     *
     * @param list
     * @return
     */
    List<String> batchCreateDocument(List<T> list);

    /**
     * 根据文档id查询
     *
     * @param id
     * @return
     */
    T findOne(String id);

    /**
     * 根据文档id查询
     *
     * @param id
     * @return
     */
    List<T> findList(List<String> id);

    /**
     * 修改文档
     *
     * @param doc
     * @return
     */
    boolean updateDocument(T doc);

    /**
     * 删除文档
     *
     * @param id
     * @return
     */
    boolean deleteDocument(String id);

    /**
     * 批量更新文档
     *
     * @param list
     * @return
     */
    boolean batchUpdateDocument(List<T> list);

    /**
     * 批量删除文档
     *
     * @param ids
     * @return
     */
    boolean batchDeleteDocument(List<String> ids);

}
