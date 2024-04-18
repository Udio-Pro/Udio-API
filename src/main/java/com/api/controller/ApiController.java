package com.api.controller;

import cn.hutool.extra.spring.SpringUtil;

import com.api.params.GenerateParams;
import com.api.params.GenerateSunoBase;
import com.api.params.GenerateUdioBase;
import com.api.rep.R;
import com.api.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @description: SUNO/UDIO
 * @Author: xJh
 * @Date: 2024/4/17
 */
@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class ApiController {

    @Autowired
    private Set<String> udioCookieSet;
    @Autowired
    private Cache<String, String> sessionTokenCache;


    @PostMapping("/generate")
    public R generate(@RequestBody GenerateParams data, HttpServletRequest request) {
        if (StringUtils.isEmpty(data.getApiType())){
            return R.failed("ApiType cannot be empty");
        }
        String apiType = data.getApiType();
        ApiService apiServiceImpl = getApiServiceImpl(apiType);
        Map<String, Object> stringStringMap=new HashMap<>();
        String token="";
        if(apiType.equals("Udio")){
            GenerateUdioBase udioBase = data.getUdioBase();
             stringStringMap = convertGenerateBaseToMap(udioBase);
             token=getUdioToken();
        }else if (apiType.equals("Suno")){
            GenerateSunoBase sunoBase = data.getSunoBase();
            stringStringMap = convertGenerateBaseToMap(sunoBase);
            token=getSunoRandomToken();
        }
        try {
            String resp = apiServiceImpl.generateMusic(stringStringMap,token);
            if (resp.equals("er")){
                return  R.failed("error", HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(resp);
            return R.ok(jsonNode,HttpStatus.OK.value());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/feed/{apiType}/{ids}")
    public R feed(@PathVariable String  apiType,@PathVariable String ids, HttpServletRequest request) {
        if (StringUtils.isEmpty(apiType)){
            return R.failed("ApiType cannot be empty");
        }
        ApiService apiServiceImpl = getApiServiceImpl(apiType);
        Map<String, Object> stringStringMap=new HashMap<>();
        String token="";
        if(apiType.equals("Udio")){
            token=getUdioToken();
        }else if (apiType.equals("Suno")){
            token=getSunoRandomToken();
        }
        try {
            String resp = apiServiceImpl.getFeed(ids,token);
            if (resp.equals("er")){
                return  R.failed("error", HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(resp);
            return R.ok(jsonNode,HttpStatus.OK.value());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    ApiService getApiServiceImpl(String ApiType){
        Map<String, ApiService> apiQueueProcessingMap = SpringUtil
                .getBeansOfType(ApiService.class);

        Optional<ApiService> optional = apiQueueProcessingMap.values()
                .stream()
                .filter(service -> service.support(ApiType))
                .max(Comparator.comparingInt(Ordered::getOrder));
        return optional.orElseThrow(() -> new IllegalStateException("No ordered ApiServiceImpl beans found in the application context"));
    }
    public String getUdioToken(){
        // 检查缓存是否为空
        if (udioCookieSet.isEmpty()) {
            throw new IllegalStateException("Cache is empty, cannot get a random value.");
        }
        // 获取值的列表
        List<String> values = udioCookieSet.stream().toList();
        // 随机化列表
        Collections.shuffle(values, new Random());
        // 返回第一个值作为随机值
        return values.get(0);
    }
    public String getSunoRandomToken() {
        // 检查缓存是否为空
        if (sessionTokenCache.asMap().isEmpty()) {
            throw new IllegalStateException("Cache is empty, cannot get a random value.");
        }
        // 获取值的列表
        List<String> values = Lists.newArrayList(sessionTokenCache.asMap().values());
        // 随机化列表
        Collections.shuffle(values, new Random());

        // 返回第一个值作为随机值
        return values.get(0);
    }
    public Map<String, Object> convertGenerateBaseToMap(Object generateBase) {
        Map<String, Object> map = new HashMap<>();

        Field[] fields = generateBase.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(generateBase);
                if (value != null ) {
                    map.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return map;
    }



}
