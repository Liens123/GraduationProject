package org.example.graduation_project.util;

import lombok.SneakyThrows;
import okhttp3.*;
import org.springframework.context.annotation.Bean; // For OkHttpClient bean
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component; // Make HttpUtil a component
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component // Mark as a Spring bean
public class HttpUtil {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient client; // Inject OkHttpClient

    public HttpUtil(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }
    /**
     * Sends a POST request with JSON body and Bearer token authentication.
     * Method is now non-static.
     *
     * @param url   The target URL.
     * @param json  The JSON payload as a String.
     * @param token The Bearer token (without "Bearer ").
     * @return The response body as a String.
     * @throws IOException           If the request could not be executed due to cancellation, connectivity problem or timeout.
     * @throws IllegalStateException If the response is not successful (e.g., 4xx, 5xx).
     */
    @SneakyThrows // Re-evaluate if SneakyThrows is appropriate, explicit throws might be better
    public String postJson(String url, String json, String token) {
        RequestBody body = RequestBody.create(json, JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/json")
                .post(body);

        if (token != null && !token.isEmpty()) {
            requestBuilder.header("Authorization", "Bearer " + token);
        }

        Request request = requestBuilder.build();

        try (Response response = this.client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            String responseBodyString = (responseBody != null) ? responseBody.string() : "";

            if (!response.isSuccessful()) {
                throw new IOException("HTTP request failed with status code " + response.code() + ": " + responseBodyString);
            }
            return responseBodyString;
        }
    }
}