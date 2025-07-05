package ru.perm.v.vacancy_j.conf;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.concurrent.TimeUnit;

// Конфигурацию кеширования можно вынести в application.yaml
//spring.cache.cache-names=StudentCache, CourseCache
//spring.cache.caffeine.spec=initialCapacity=50,maximumSize=10,expireAfterAccess=300s
@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(300, TimeUnit.SECONDS)
                .initialCapacity(10);
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}