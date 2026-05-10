package com.septeo.ulyses.technical.test.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        // using a simple in-memory cache for demonstration purposes
        // in production, you might want to use a more robust caching solution like Redis or Ehcache
        return new ConcurrentMapCacheManager("brands");
    }
}
