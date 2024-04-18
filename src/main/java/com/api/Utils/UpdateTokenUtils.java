package com.api.Utils;

import com.google.common.cache.Cache;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @Author: xJh
 * @Date: 2024/4/11
 */
@Component
public class UpdateTokenUtils {
    private final Cache<String, String> sessionCookieCache;
    private final Cache<String, String> sessionTokenCache;
    private Thread keepAliveThread;
    private volatile boolean running = true;

    @Autowired
    public UpdateTokenUtils(Cache<String, String> sessionCookieCache, Cache<String, String> sessionTokenCache) {
        this.sessionCookieCache = sessionCookieCache;
        this.sessionTokenCache = sessionTokenCache;
        startKeepAliveThread();
    }
    private void startKeepAliveThread() {
        keepAliveThread = new Thread(() -> {
            while (running) {
                try {
                    updateTokenPare();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    running = false;
                }
            }
        });
        keepAliveThread.setDaemon(true); // 在JVM退出时自动停止
        keepAliveThread.start();
    }
    public void updateTokenPare() throws Exception{
        for (Map.Entry<String, String> entry : sessionCookieCache.asMap().entrySet()) {
            String sessionId = entry.getKey();
            String cookie = entry.getValue();
            updateToken(sessionId,cookie);
        }
    }
    private void updateToken(String sessionId, String cookie) {
        try {
            URL url = new URL("https://clerk.suno.com/v1/client/sessions/" + sessionId + "/tokens?_clerk_js_version=4.72.0-snapshot.vc141245");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Cookie", cookie);
            // 设置Content-Type为text/plain
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // 设置其他请求头
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
            connection.setRequestProperty("Referer", "https://suno.com/");
            connection.setRequestProperty("Origin", "https://suno.com");


            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Map<String, List<String>> headers = connection.getHeaderFields();
               // System.out.println(headers.toString());
                List<String> cookiesHeader = headers.get("Set-Cookie");
             //   System.out.println(cookiesHeader);
                String setCookie = connection.getHeaderField("Set-Cookie");
                StringBuffer builder = new StringBuffer();

                // 定义正则表达式来匹配_client的值
                String regex = "__client=([^;]+);";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(cookie);
                String clientValue="";
                if (matcher.find()) {
                    clientValue= matcher.group(); // 获取整个匹配部分，包括__client=和后面的值，直到分号
               //     System.out.println("Extracted __client value: " + clientValue);
                } else {
                 //   System.out.println("No match found for __client.");
                }
                builder.append(clientValue);
             //   builder.append("__client=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImNsaWVudF8yZXVRZWdyeExqT3Q5U1NRNmhYQnpHeWFKYmoiLCJyb3RhdGluZ190b2tlbiI6ImhxOGc2YjhzaXJyZWRubTJkejM2Y3c1MmV0NHR2dDdyb29tcHdhZGgifQ.InLS9NyWJXntt-WR5hWshj-hQ51wOWi-nGo0i44BGD8Kn_pOi49EfCMXT303oOKfOLpthVvOfNsmKT_1mYjU5pCzHplwl0YFmRbmqTi4GCqiH37BszvKkXy12ZDxFr4nZ0WLYcRTW9Pin2pq5tCMtaJO9JdMpZsvhhgWcILMvTfhelScTkKtq3lJoRGncyQKC9Egwi5SkYQrNOH_gCICyMX4rqpwzlcUbqss98LExSBtJh12s7XFlrFqmVhopEkIwG-tXR1szrNIiHColBdB3VJDD6ye2mYl-XLEhRDYsPulUYLxkyeBiAIslhff9wTenY4KaszP53c0VSQRd3vQ4g;");
                for (String s : cookiesHeader) {
                    builder.append(s+";");
                }
                sessionCookieCache.put(sessionId,builder.toString());
                String responseString = response.toString(); // 确保 response 可以被转换为字符串
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(responseString).getAsJsonObject();
                String jwt = jsonObject.get("jwt").getAsString();
                // 设置 sunoCookie 的 token
                //sunoCookie.setToken(jwt);
                // 更新 sunoCookie
                sessionTokenCache.put(sessionId, jwt);
             //   System.out.println("更新成功....获取到的jwt.."+jwt);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println("更新失败...."+response.toString());
                System.out.println("GET request not worked");
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
