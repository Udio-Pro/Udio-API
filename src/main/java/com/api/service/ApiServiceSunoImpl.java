package com.api.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @Author: xJh
 * @Date: 2024/4/17
 */
@Service("apiServiceSunoImpl")
@Slf4j
public class ApiServiceSunoImpl  implements ApiService{
    private static final String BASE_URL = "https://studio-api.suno.ai";
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build();
    private static final Gson gson = new Gson();

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @Override
    public boolean support(String ApiType) {
        return  ApiType.equals("Suno");
    }



    public  String getFeed(String ids, String token) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/feed/?ids=" + ids)
                .header("Content-Type","text/plain;charset=UTF-8")
                .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                .header("Referer","https://suno.com/")
                .header("Origin","https://suno.com")
                .header("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String resp = response.body().string();
            log.info("getFeed........{}", resp);
            if (!response.isSuccessful()) {
                return "er";
            }
            return resp;
        } catch (SocketTimeoutException e) {
            return "timeout";
        } catch (IOException e) {
            e.printStackTrace();
            return "er";
        }
    }

    public  String generateMusic(Map<String, Object> data, String token) {
        RequestBody body = RequestBody.create(JSON, gson.toJson(data));
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/generate/v2/")
                .header("Content-Type","text/plain;charset=UTF-8")
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                .header("Referer","https://suno.com")
                .header("Origin","https://suno.com")
                .header("Authorization", "Bearer " + token)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String resp = response.body().string();
            log.info("response...{}",resp);
            if (!response.isSuccessful()) {
                return "er";
            }
            return resp;
        }catch (Exception e){
            log.error("[Api-Suno ] error...{}",e.getMessage());
            return "er";
        }
    }

    public static String generateLyrics(String prompt, String token)  {
        Map<String, String> payload = new HashMap<>();
        payload.put("prompt", prompt);
        RequestBody body = RequestBody.create(JSON, gson.toJson(payload));
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/generate/lyrics/")
                .header("Content-Type","text/plain;charset=UTF-8")
                .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                .header("Referer","https://suno.com")
                .header("Origin","https://suno.com")
                .header("Authorization", "Bearer " + token)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public static String getLyrics(String lid, String token) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/generate/lyrics/" + lid)
                .header("Content-Type","text/plain;charset=UTF-8")
                .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                .header("Referer","https://suno.com")
                .header("Origin","https://suno.com")
                .header("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
