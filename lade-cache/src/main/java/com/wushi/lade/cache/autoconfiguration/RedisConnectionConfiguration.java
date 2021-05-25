/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wushi.lade.cache.autoconfiguration;

import com.wushi.lade.cache.properties.RedisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base Redis connection configuration.
 *
 * @author Mark Paluch
 * @author Stephane Nicoll
 * @author Alen Turkovic
 */
@ConditionalOnProperty(prefix = "wushi.cache", name = "type", havingValue = "redis")
abstract class RedisConnectionConfiguration {

	private final RedisProperties properties;

	private final RedisSentinelConfiguration sentinelConfiguration;

	private final RedisClusterConfiguration clusterConfiguration;

	protected RedisConnectionConfiguration(RedisProperties properties,
                                           ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider,
                                           ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider) {
		this.properties = properties;
		this.sentinelConfiguration = sentinelConfigurationProvider.getIfAvailable();
		this.clusterConfiguration = clusterConfigurationProvider.getIfAvailable();
	}

	protected final RedisStandaloneConfiguration getStandaloneConfig() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		if (StringUtils.hasText(this.properties.getUrl())) {
			ConnectionInfo connectionInfo = parseUrl(this.properties.getUrl());
			config.setHostName(connectionInfo.getHostName());
			config.setPort(connectionInfo.getPort());
			config.setPassword(RedisPassword.of(connectionInfo.getPassword()));
		}
		else {
			config.setHostName(this.properties.getHost());
			config.setPort(this.properties.getPort());
			config.setPassword(RedisPassword.of(this.properties.getPassword()));
		}
		config.setDatabase(this.properties.getDatabase());
		return config;
	}

	protected final RedisSentinelConfiguration getSentinelConfig() {
		if (this.sentinelConfiguration != null) {
			return this.sentinelConfiguration;
		}
		RedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
		if (sentinelProperties != null) {
			RedisSentinelConfiguration config = new RedisSentinelConfiguration();
			config.master(sentinelProperties.getMaster());
			config.setSentinels(createSentinels(sentinelProperties));
			if (this.properties.getPassword() != null) {
				config.setPassword(RedisPassword.of(this.properties.getPassword()));
			}
			config.setDatabase(this.properties.getDatabase());
			return config;
		}
		return null;
	}

	/**
	 * Create a {@link RedisClusterConfiguration} if necessary.
	 * @return {@literal null} if no cluster settings are set.
	 */
	protected final RedisClusterConfiguration getClusterConfiguration() {
		if (this.clusterConfiguration != null) {
			return this.clusterConfiguration;
		}
		if (this.properties.getCluster() == null) {
			return null;
		}
		RedisProperties.Cluster clusterProperties = this.properties.getCluster();
		RedisClusterConfiguration config = new RedisClusterConfiguration(clusterProperties.getNodes());
		if (clusterProperties.getMaxRedirects() != null) {
			config.setMaxRedirects(clusterProperties.getMaxRedirects());
		}
		if (this.properties.getPassword() != null) {
			config.setPassword(RedisPassword.of(this.properties.getPassword()));
		}
		return config;
	}

	protected final RedisProperties getProperties() {
		return this.properties;
	}

	private List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
		List<RedisNode> nodes = new ArrayList<>();
		for (String node : sentinel.getNodes()) {
			try {
				String[] parts = StringUtils.split(node, ":");
				Assert.state(parts.length == 2, "Must be defined as 'host:port'");
				nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
			}
			catch (RuntimeException ex) {
				throw new IllegalStateException("Invalid redis sentinel property '" + node + "'", ex);
			}
		}
		return nodes;
	}

	protected ConnectionInfo parseUrl(String url) {
		try {
			URI uri = new URI(url);
			boolean useSsl = (url.startsWith("rediss://"));
			String password = null;
			if (uri.getUserInfo() != null) {
				password = uri.getUserInfo();
				int index = password.indexOf(':');
				if (index >= 0) {
					password = password.substring(index + 1);
				}
			}
			return new ConnectionInfo(uri, useSsl, password);
		}
		catch (URISyntaxException ex) {
			throw new IllegalArgumentException("Malformed url '" + url + "'", ex);
		}
	}

	static class ConnectionInfo {

		private final URI uri;

		private final boolean useSsl;

		private final String password;

		ConnectionInfo(URI uri, boolean useSsl, String password) {
			this.uri = uri;
			this.useSsl = useSsl;
			this.password = password;
		}

		boolean isUseSsl() {
			return this.useSsl;
		}

		String getHostName() {
			return this.uri.getHost();
		}

		int getPort() {
			return this.uri.getPort();
		}

		String getPassword() {
			return this.password;
		}

	}

}
