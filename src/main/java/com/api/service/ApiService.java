package com.api.service;

import org.springframework.core.Ordered;

import java.util.Map;

/**
 * @description: SUNO/UDIO通用接口
 * @Author: xJh
 * @Date: 2024/4/17
 */
public interface ApiService  extends Ordered {
     default boolean support(String apiType){
        return true;
    }
     String generateMusic(Map<String, Object> data, String token);
     //check task status
     String getFeed(String ids, String token);
}
