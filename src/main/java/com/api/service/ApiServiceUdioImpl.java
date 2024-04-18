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
@Service("apiServiceUdioImpl")
@Slf4j
public class ApiServiceUdioImpl  implements ApiService{
    private static final String BASE_URL = "https://www.udio.com/api";
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS) // 连接超时时间
            .readTimeout(20, TimeUnit.SECONDS) // 读取超时时间
            .writeTimeout(20, TimeUnit.SECONDS) // 写入超时时间
            .build();
    private static final Gson gson = new Gson();

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String Token = "%5B%22eyJhbGciOiJIUzI1NiIsImtpZCI6IlJHVktoVzNNcSsyVzhxcDkiLCJ0eXAiOiJKV1QifQ.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoxNzEzMjc5MTMxLCJpYXQiOjE3MTMyNzU1MzEsImlzcyI6Imh0dHBzOi8vbWZtcHhqZW1hY3NoZmNwem9zbHUuc3VwYWJhc2UuY28vYXV0aC92MSIsInN1YiI6ImQyM2E0NDM0LTVjODMtNDg3Yy1hNDI1LTU1ZjAzNjc2MmFiOCIsImVtYWlsIjoienVja2RvbnRAZ21haWwuY29tIiwicGhvbmUiOiIiLCJhcHBfbWV0YWRhdGEiOnsicHJvdmlkZXIiOiJnb29nbGUiLCJwcm92aWRlcnMiOlsiZ29vZ2xlIl19LCJ1c2VyX21ldGFkYXRhIjp7ImF2YXRhcl91cmwiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NMTk1oV3dKbzF2R0tsT2FMWUlMVzFPdDQ2THZYaXJLM3JFYTBWNU5idy1vUkNVWGNzPXM5Ni1jIiwiZW1haWwiOiJ6dWNrZG9udEBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZnVsbF9uYW1lIjoid2FuZyBoYW9kb25nIiwiaXNzIjoiaHR0cHM6Ly9hY2NvdW50cy5nb29nbGUuY29tIiwibmFtZSI6IndhbmcgaGFvZG9uZyIsInBob25lX3ZlcmlmaWVkIjpmYWxzZSwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0xOTWhXd0pvMXZHS2xPYUxZSUxXMU90NDZMdlhpckszckVhMFY1TmJ3LW9SQ1VYY3M9czk2LWMiLCJwcm92aWRlcl9pZCI6IjExMjM3NzE3MjE1NTQxNjMwMDUzOSIsInN1YiI6IjExMjM3NzE3MjE1NTQxNjMwMDUzOSJ9LCJyb2xlIjoiYXV0aGVudGljYXRlZCIsImFhbCI6ImFhbDEiLCJhbXIiOlt7Im1ldGhvZCI6Im9hdXRoIiwidGltZXN0YW1wIjoxNzEzMjc1NTMxfV0sInNlc3Npb25faWQiOiJmMTAzYmJkNS1lNmI2LTRlNmYtOWQ1ZS01MGY5MWU5ODU1NDYiLCJpc19hbm9ueW1vdXMiOmZhbHNlfQ.Q4T4PCEI97nnAazAary9gadcx_jTv13R306lV7Vxqm4%22%2C%22bIZ_bkfrx--lJaVU4v-fsQ%22%2C%22ya29.a0Ad52N3-bBPpXuqiBAxB-h4at0stQ7mzDOzKMUIjSUXxT6-8puIxbhVU6qIwF7zQ9M0jjkB2THweYbnLoQ9ekkEk3dBRnrVe7vEBWdR8wAFBXBVIvFSMaS076Fncbiyj-Ap4A0qbck_dlkeifsDxVqEHWNcCdecKPzS4aCgYKAQwSARESFQHGX2MihNB_7qIqgDB-e6y4vGC0EQ0170%22%2Cnull%2Cnull%5D";

    @Override
    public boolean support(String ApiType) {
        return  ApiType.equals("Udio");
    }

    /*
     * @param data
     *  short song、extend song
     * @throws IOException
     */
    public  String generateMusic(Map<String, Object> data, String token)  {
        Map<String, Object> udioData = new HashMap<>();

        udioData.put("prompt", data.get("udPrompt"));
        //custom Lyrics
        if(data.get("udCustomLyrics")!=null||data.get("udCustomLyrics").toString().length()>0){
            udioData.put("lyricInput", data.get("udCustomLyrics"));
        }else if (data.get("udCustomLyrics")!=null||data.get("udCustomLyrics").toString().equals("")){
            //Generate Instrumental
            udioData.put("lyricInput", "");
        }
        //The system automatically generates lyrics without the need to pass in lyricInput

        //other
        Map<String, Object> udPamplerOptions = new HashMap<>();
        udPamplerOptions.put("seed", -1);
        //--------Required parameters for expanding songs start with
        //The path of the extended song
        String audioConditioningPath = (String) data.get("udAudioConditioningPath");
        udPamplerOptions.put("audio_conditioning_path", (audioConditioningPath != null) ? audioConditioningPath : "");
        //The ID of the extended song
        String udAudioConditioningSongId = (String) data.get("udAudioConditioningSongId");
        udPamplerOptions.put("audio_conditioning_song_id",  (udAudioConditioningSongId != null) ? udAudioConditioningSongId : "");
        //Extend the placement of lyrics without distinction
        //continuation is before ，precede is after
        String udAudioConditioningType = (String) data.get("udAudioConditioningType");
        udPamplerOptions.put("audio_conditioning_type",(udAudioConditioningType != null) ? udAudioConditioningType : "");
        //--------Required parameters for expanding songs - End
        udioData.put("samplerOptions",udPamplerOptions);
        RequestBody body = RequestBody.create(JSON, gson.toJson(udioData));
        Request request = new Request.Builder()
                .url(BASE_URL + "/generate-proxy")
                .header("Accept","application/json, text/plain, */*")
                .header("Content-Type","application/json")
                .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                .header("Referer","https://www.udio.com/my-creations")
                .header("Origin","https://www.udio.com")
                .header("Cookie",token)
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
            log.error("[Api-Uido ] error...{}",e.getMessage());
            return "er";
        }
    }

    /**
     * check task  status
     * @param ids
     * @param token
     * @return
     * @throws IOException
     */
    public  String getFeed(String ids, String token)  {
        Request request = new Request.Builder()
                .url(BASE_URL + "/songs?songIds=" + ids)
                .header("Accept","application/json, text/plain, */*")
                .header("Content-Type","application/json")
                .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                .header("Referer","https://www.udio.com/my-creations")
                .header("Origin","https://www.udio.com")
                .header("Cookie",token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String resp = response.body().string();
            log.info("[Udio.]getFeed........{}", resp);
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



    public static String generateLyrics(String prompt, String token) throws IOException {
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
        return 1;
    }


}
