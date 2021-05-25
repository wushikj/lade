package com.wushi.lade.es.config;

import com.wushi.lade.es.properties.EsProperties;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ESConfig {

    @Autowired
    private EsProperties esProperties;

    @Bean
    public RestHighLevelClient getRestHighLevelClient() {

        List<HttpHost> httpHosts = new ArrayList<>();

        for (String node : esProperties.getNodes()) {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.notNull(parts, "Must defined");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                httpHosts.add(new HttpHost(parts[0], Integer.parseInt(parts[1]), esProperties.getSchema()));
            } catch (RuntimeException ex) {
                throw new IllegalStateException(
                        "Invalid ES nodes " + "property '" + node + "'", ex);
            }
        }
        HttpHost[] httpHostArr = httpHosts.toArray(new HttpHost[0]);
        RestClientBuilder builder = RestClient.builder(httpHostArr);

        String userName = esProperties.getUsername();
        String password = esProperties.getPassword();

        if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password)) {
            // 在请求头增加安全认证
            String auth = Base64.encodeBase64String((userName + ":" + password).getBytes());
            builder.setDefaultHeaders(new BasicHeader[]{new BasicHeader("Authorization", "Basic " + auth)});


            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(userName, password));
            builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                    return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }
            });
        }


        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(esProperties.getConnectTimeoutMillis());
            requestConfigBuilder.setSocketTimeout(esProperties.getSocketTimeoutMillis());
            requestConfigBuilder.setConnectionRequestTimeout(esProperties.getConnectionRequestTimeoutMillis());
            return requestConfigBuilder;
        });

        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(esProperties.getMaxConnectTotal());
            httpClientBuilder.setMaxConnPerRoute(esProperties.getMaxConnectPerRoute());
            return httpClientBuilder;
        });


        return new RestHighLevelClient(builder);
    }

    @Bean
    public EsIndexInitConfig indexInitConfig() {
        return new EsIndexInitConfig();
    }
}

