package com.wushi.lade.es.dao;

import com.wushi.lade.es.annotations.BaseDocument;
import com.wushi.lade.es.annotations.EsDocument;
import com.wushi.lade.es.util.ElasticsearchUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author wushi
 * @date 2020-07-31
 */
public abstract class BaseEsDaoImpl<T extends BaseDocument> implements BaseEsDao<T> {

    @Override
    public String createDocument(T doc) {
        return ElasticsearchUtil.createDocument(doc.getEsIndexName(), doc);
    }

    @Override
    public List<String> batchCreateDocument(List<T> list) {
        return ElasticsearchUtil.batchCreateDocument(list);
    }

    @Override
    public T findOne(String id) {

        return ElasticsearchUtil.findOne(getIndexName(), id, getCurrentGenericType());
    }

    @Override
    public List<T> findList(List<String> id) {
        return ElasticsearchUtil.findList(getIndexName(), id, getCurrentGenericType());
    }

    @Override
    public boolean updateDocument(T doc) {
        return ElasticsearchUtil.updateDocument(doc.getEsIndexName(), doc);
    }

    @Override
    public boolean deleteDocument(String id) {
        Class<T> clz = getCurrentGenericType();
        EsDocument esDocument = clz.getAnnotation(EsDocument.class);
        return ElasticsearchUtil.deleteDocument(getIndexName(), id);
    }

    @Override
    public boolean batchUpdateDocument(List<T> list) {
        return ElasticsearchUtil.batchUpdateDocument(getIndexName(), list);
    }


    @Override
    public boolean batchDeleteDocument(List<String> ids) {
        return ElasticsearchUtil.batchDeleteDocument(getIndexName(), ids);
    }

    private Class<T> getCurrentGenericType() {
        Type t = this.getClass().getGenericSuperclass();
        Type genericType = ((ParameterizedType) t).getActualTypeArguments()[0];
        try {
            return (Class<T>) (Class.forName(genericType.getTypeName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String getIndexName() {
        Class<T> clz = getCurrentGenericType();
        EsDocument esDocument = clz.getAnnotation(EsDocument.class);
        return esDocument.indexName();
    }

}
