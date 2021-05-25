package com.wushi.lade.es.annotations;


import java.lang.annotation.*;


/**
 * @author wushi
 * @date 2020/7/28 15:53
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EsDocument {
    String indexName();

    boolean useServerConfiguration() default false;

    short shards() default 5;

    short replicas() default 1;

    String refreshInterval() default "1s";

    String indexStoreType() default "fs";

    boolean createIndex() default true;
}
