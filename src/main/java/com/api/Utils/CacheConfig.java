package com.api.Utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @description:
 * @Author: xJh
 * @Date: 2024/4/11
 */
@Configuration
@EnableAsync
public class CacheConfig {

    //Session - cookie 池
    @Bean
    public Cache<String, String> sessionCookieCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build();
    }

    //seeionId -- jwt 池
    @Bean
    public Cache<String, String> sessionTokenCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build();
    }
    @Bean
    public Set<String> udioCookieSet()
    {
        return new CopyOnWriteArraySet<>();
    }

}
