package org.example.graduation_project.util;


import lombok.SneakyThrows;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

public class HttpUtil {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");
    private static final MediaType TEXT_XML = MediaType.parse("text/xml; charset=utf-8");
    private static final MediaType MULTIPART_FORM_DATA = MediaType.parse("multipart/form-data");
    private static final int HTTP_TIMEOUT = 300;


    private static final OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
            .build();

    public static OkHttpClient getClient() {return client;}

    @SneakyThrows
    public static String postJson(String url, String json, String token) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "";
                throw new RuntimeException("HTTP请求失败，状态码：" + response.code() + "，响应：" + errorBody);
            }
            return response.body() != null ? response.body().string() : "";
        }
    }


}
