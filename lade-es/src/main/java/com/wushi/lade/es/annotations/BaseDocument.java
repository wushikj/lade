package com.wushi.lade.es.annotations;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BaseDocument
 *
 * @author wushi
 * @date 2020-07-31
 */
public abstract class BaseDocument implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_INDEX_NAME = "_default";
    public static final int DEFAULT_SHARDS = 1;
    public static final int DEFAULT_REPLICAS = 1;

    public String parseDocId() {
        boolean isDocIdEnable = true;
        String docId = null;

        List<Field> fields = new ArrayList<>(Arrays.asList(this.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(this.getClass().getSuperclass().getDeclaredFields()));
        for (Field field : fields) {
            try {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if (field.isAnnotationPresent(EsDocId.class)) {
                    if (field.get(this) instanceof String
                            || field.get(this) instanceof Long
                            || field.get(this) instanceof Integer) {
                        docId = field.get(this).toString();
                        break;
                    }
                }
                field.setAccessible(isAccessible);
            } catch (SecurityException | IllegalArgumentException
                    | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return docId;
    }

    public String getEsIndexName() {
        EsDocument esDocument = this.getClass().getAnnotation(EsDocument.class);
        if (esDocument == null) {
            return DEFAULT_INDEX_NAME;
        }
        String indexName = esDocument.indexName();
        if (StringUtils.isEmpty(indexName)) {
            return DEFAULT_INDEX_NAME;
        }
        return indexName;
    }

    public int getEsShards() {
        EsDocument esDocument = this.getClass().getAnnotation(EsDocument.class);
        if (esDocument == null) {
            return DEFAULT_SHARDS;
        }
        int shards = esDocument.shards();
        if (shards <= 0) {
            return DEFAULT_SHARDS;
        }
        return DEFAULT_SHARDS;
    }

    public int getEsReplicas() {
        EsDocument esDocument = this.getClass().getAnnotation(EsDocument.class);
        if (esDocument == null) {
            return DEFAULT_REPLICAS;
        }
        int replicas = esDocument.replicas();
        if (replicas <= 0) {
            return DEFAULT_REPLICAS;
        }
        return DEFAULT_REPLICAS;
    }

}
