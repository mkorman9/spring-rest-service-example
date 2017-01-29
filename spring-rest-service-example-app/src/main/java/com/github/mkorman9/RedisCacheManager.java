package com.github.mkorman9;

import org.hibernate.cache.redis.client.RedisClient;
import org.hibernate.cache.redis.client.RedisClientFactory;
import org.hibernate.cache.redis.hibernate4.SingletonRedisRegionFactory;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import java.util.Properties;

public class RedisCacheManager extends SingletonRedisRegionFactory {
    private String address;

    public RedisCacheManager(Properties props) {
        super(props);
        address = props.getProperty("cache.address");
    }

    @Override
    public RedisClient createRedisClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(address)
                .setIdleConnectionTimeout(10000)
                .setPingTimeout(1000)
                .setConnectTimeout(1000)
                .setTimeout(1000)
                .setRetryAttempts(1)
                .setRetryInterval(1000)
                .setReconnectionTimeout(3000)
                .setFailedAttempts(1)
                .setSubscriptionsPerConnection(5)
                .setSubscriptionConnectionMinimumIdleSize(1)
                .setSubscriptionConnectionPoolSize(25)
                .setConnectionMinimumIdleSize(5)
                .setConnectionPoolSize(100)
                .setDatabase(0)
                .setDnsMonitoring(false)
                .setDnsMonitoringInterval(5000);
        config.setThreads(0);
        config.setCodec(new JsonJacksonCodec());
        config.setUseLinuxNativeEpoll(false);
        config.setEventLoopGroup(null);
        return RedisClientFactory.createRedisClient(config);
    }
}
