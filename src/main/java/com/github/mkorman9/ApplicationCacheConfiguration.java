package com.github.mkorman9;

import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableSet;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class ApplicationCacheConfiguration {
    @Bean
    public CacheManager cacheManager() {
        GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
        guavaCacheManager.setCacheBuilder(CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS));
        guavaCacheManager.setCacheNames(ImmutableSet.of("someCacheRegion"));
        return guavaCacheManager;
    }
}
