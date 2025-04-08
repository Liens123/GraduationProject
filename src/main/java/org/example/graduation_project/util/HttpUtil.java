package org.example.graduation_project.util;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;

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



}
