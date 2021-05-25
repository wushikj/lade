package com.wushi.lade.es.util;

import com.wushi.lade.common.utils.JsonUtils;
import com.wushi.lade.es.annotations.BaseDocument;
import com.wushi.lade.es.dto.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wushi
 * @date 2020-07-31
 */
@Component
public class ElasticsearchUtil implements ApplicationContextAware {

    private static RestHighLevelClient restHighLevelClient;
    public static final String _TYPE = "_doc";
    private static Logger log = LoggerFactory.getLogger(ElasticsearchUtil.class);


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        restHighLevelClient = applicationContext.getBean(RestHighLevelClient.class);
    }
    /*------------------------------------------索引API------------------------------------------*/

    /**
     * 创建索引
     *
     * @param index index名必须全小写，否则报错
     * @return
     */
    public static boolean createIndex(String index) {
        CreateIndexRequest request = new CreateIndexRequest(index);
        try {
            CreateIndexResponse indexResponse = restHighLevelClient.
                    indices().
                    create(request, RequestOptions.DEFAULT);
            if (indexResponse.isAcknowledged()) {
                log.info(String.format("create es index {} success", index));
            } else {
                log.error(String.format("create es index {} fail", index));
            }
            return indexResponse.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建索引
     *
     * @param index index名必须全小写，否则报错
     * @return
     */
    public static boolean createIndex(String index, int shards, int replicas, String settingPath, String mappingPath) {
        CreateIndexRequest request = new CreateIndexRequest(index);
        shards = shards > 0 ? shards : 1;
        replicas = replicas > 0 ? replicas : 1;
        // 设置
        if (StringUtils.isNotEmpty(settingPath)) {
            String settingSource = FileUtil.readFileFromClasspathNew(settingPath);
            request.settings(settingSource, XContentType.JSON);
        } else {
            request.settings(Settings.builder()
                    .put("index.number_of_shards", shards)
                    .put("index.number_of_replicas", replicas)
            );
        }
        // mapping配置
        if (StringUtils.isNotEmpty(mappingPath)) {
            String mappingSource = FileUtil.readFileFromClasspathNew(mappingPath);
            request.mapping(_TYPE, mappingSource, XContentType.JSON);
        }

        try {
            CreateIndexResponse indexResponse = restHighLevelClient.
                    indices().
                    create(request, RequestOptions.DEFAULT);
            if (indexResponse.isAcknowledged()) {
                log.info(String.format("create es index {} success", index));
            } else {
                log.error(String.format("create es index {} fail", index));
            }
            return indexResponse.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public static boolean deleteIndex(String index) {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        try {
            AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 校验索引是否存在
     *
     * @param index
     * @return
     */
    public static boolean checkIndexExists(String index) {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(index);
        try {
            return restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*------------------------------------------索引API------------------------------------------*/


    /*------------------------------------------文档API------------------------------------------*/

    /**
     * 插入数据
     *
     * @param index
     * @param object
     * @return
     */
    public static <T extends BaseDocument> String createDocument(String index, T object) {
        String id = object.parseDocId();
        IndexRequest indexRequest = new IndexRequest(index, _TYPE, id);
        try {
            indexRequest.source(JsonUtils.toJSONString(object), XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            return indexResponse.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 批量插入数据
     *
     * @param index
     * @param list
     * @return
     */
    public static <T extends BaseDocument> List<String> batchCreateDocument(String index, List<T> list) {
        List<String> res = new ArrayList<>();
        BulkRequest bulkRequest = new BulkRequest();
        for (T t : list) {
            String id = t.parseDocId();
            IndexRequest indexRequest = new IndexRequest(index, _TYPE, id);
            indexRequest.source(JsonUtils.toJSONString(t), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        try {
            BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkItemResponses != null) {
                for (BulkItemResponse bulkItemResponse : bulkItemResponses.getItems()) {
                    res.add(bulkItemResponse.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 批量插入数据
     *
     * @param list
     * @return
     */
    public static <T extends BaseDocument> List<String> batchCreateDocument(List<T> list) {
        List<String> res = new ArrayList<>();
        BulkRequest bulkRequest = new BulkRequest();
        for (T t : list) {
            String id = t.parseDocId();
            IndexRequest indexRequest = new IndexRequest(t.getEsIndexName(), _TYPE, id);
            indexRequest.source(JsonUtils.toJSONString(t), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        try {
            BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkItemResponses != null) {
                for (BulkItemResponse bulkItemResponse : bulkItemResponses.getItems()) {
                    res.add(bulkItemResponse.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 根据id查询
     *
     * @param index
     * @param id
     * @param <T>
     * @return
     */
    public static <T extends BaseDocument> T findOne(String index, String id, Class<T> tClass) {
        GetRequest getRequest = new GetRequest(index, _TYPE, id);
        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                return JsonUtils.parseObject(getResponse.getSourceAsString(), tClass);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据id查询
     *
     * @param index
     * @param id
     * @param <T>
     * @return
     */
    public static <T extends BaseDocument> List<T> findList(String index, List<String> id, Class<T> tClass) {
        MultiGetRequest getRequest = new MultiGetRequest();
        id.forEach(item -> {
            getRequest.add(index, item);
        });
        List<T> list = new ArrayList<>();
        try {
            MultiGetResponse mget = restHighLevelClient.mget(getRequest, RequestOptions.DEFAULT);
            if (mget.getResponses().length > 0) {
                for (MultiGetItemResponse getResponse : mget.getResponses()) {
                    if (getResponse.getResponse().getSourceAsString() != null) {
                        T result = JsonUtils.parseObject(getResponse.getResponse().getSourceAsString(), tClass);
                        list.add(result);
                    }
                }
                return list;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文档
     *
     * @param index
     * @param id
     * @return
     */
    public static boolean deleteDocument(String index, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(index, _TYPE, id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            if (deleteResponse != null) {
//                deleteResponse.status();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 批量删除文档
     *
     * @param index
     * @param ids
     * @return
     */
    public static boolean batchDeleteDocument(String index, List<String> ids) {

        BulkRequest bulkRequest = new BulkRequest();
        for (String id : ids) {
            DeleteRequest deleteRequest = new DeleteRequest(index, _TYPE, id);
            bulkRequest.add(deleteRequest);
        }
        try {
            BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkItemResponses != null) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更新文档
     *
     * @param index
     * @param list
     * @param <T>
     * @return
     */
    public static <T extends BaseDocument> boolean batchUpdateDocument(String index, List<T> list) {
        BulkRequest bulkRequest = new BulkRequest();
        for (T t : list) {
            String id = t.parseDocId();
            UpdateRequest updateRequest = new UpdateRequest(index, _TYPE, id);
            updateRequest.doc(JsonUtils.toJSONString(t), XContentType.JSON);
            bulkRequest.add(updateRequest);
        }

        try {
            BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkItemResponses != null) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 批量更新文档
     *
     * @param index
     * @param object
     * @param <T>
     * @return
     */
    public static <T extends BaseDocument> boolean updateDocument(String index, T object) {
        String id = object.parseDocId();
        UpdateRequest updateRequest = new UpdateRequest(index, _TYPE, id);
        updateRequest.doc(JsonUtils.toJSONString(object), XContentType.JSON);
        try {
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            if (updateResponse != null && RestStatus.OK == updateResponse.status()) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 基本搜索
     *
     * @param request
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T extends BaseDocument> SearchResult<T> search(SearchRequest request, Class<T> tClass) {
        SearchResult<T> searchResult = new SearchResult();
        try {
            SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            if (searchResponse != null) {
                SearchHits hits = searchResponse.getHits();
                searchResult.setTotalHits(hits.getTotalHits().value);
                List<T> data = new ArrayList<>();
                for (SearchHit hit : hits) {
                    T d = JsonUtils.parseObject(hit.getSourceAsString(), tClass);
                    data.add(d);
                }
                searchResult.setData(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    /*------------------------------------------文档API------------------------------------------*/


    /**
     * 获取低级客户端
     *
     * @return
     */
    public static RestClient getLowLevelClient() {
        return restHighLevelClient.getLowLevelClient();
    }

    /**
     * 获取高级客户端
     */
    public static RestHighLevelClient getHighLevelClient() {
        return restHighLevelClient;
    }
}
