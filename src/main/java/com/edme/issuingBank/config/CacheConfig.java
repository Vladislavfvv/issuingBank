package com.edme.issuingBank.config;

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
        return new ConcurrentMapCacheManager();
    }


    //c настройкой времени TTL
    //@Bean
    //public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
    //    RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
    //        .entryTtl(Duration.ofMinutes(30)) // 30 минут кэш
    //        .disableCachingNullValues();
    //
    //    return RedisCacheManager.builder(factory)
    //        .cacheDefaults(cacheConfig)
    //        .build();
    //}
}
