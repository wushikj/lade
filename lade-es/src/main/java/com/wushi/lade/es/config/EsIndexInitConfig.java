package com.wushi.lade.es.config;

import com.wushi.lade.es.annotations.EsDocument;
import com.wushi.lade.es.annotations.EsMapping;
import com.wushi.lade.es.annotations.EsSetting;
import com.wushi.lade.es.dao.BaseEsDao;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.wushi.lade.es.util.ElasticsearchUtil;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author wushi
 * @date 2020-07-31
 */
public class EsIndexInitConfig implements ApplicationContextAware {
    //
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @PostConstruct
    public void initIndex() throws ClassNotFoundException {
        Map<String, BaseEsDao> map = context.getBeansOfType(BaseEsDao.class);
        for (BaseEsDao baseEsDao : map.values()) {
            Type t = baseEsDao.getClass().getSuperclass().getGenericSuperclass();
            Type genericType = ((ParameterizedType) t).getActualTypeArguments()[0];
            Class<?> clz = Class.forName(genericType.getTypeName());
            EsDocument esDocument = clz.getAnnotation(EsDocument.class);

            String settingPath = null;
            EsSetting esSetting = clz.getAnnotation(EsSetting.class);
            if (esSetting != null) {
                settingPath = esSetting.settingPath();
            }

            String mappingPath = null;
            EsMapping esMapping = clz.getAnnotation(EsMapping.class);
            if (esMapping != null) {
                mappingPath = esMapping.mappingPath();
            }

            if (esDocument != null) {
                if (!ElasticsearchUtil.checkIndexExists(esDocument.indexName())) {
                    ElasticsearchUtil.createIndex(esDocument.indexName(),
                            esDocument.shards(),
                            esDocument.replicas(),
                            settingPath,
                            mappingPath);
                }
            }
        }
    }


}
