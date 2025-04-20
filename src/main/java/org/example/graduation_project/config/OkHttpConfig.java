package org.example.graduation_project.config; // 确保包名正确

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; // 移到顶级类上
import java.util.concurrent.TimeUnit;

@Configuration // 声明这是一个配置类
public class OkHttpConfig {

    // 可以将超时时间也配置到 application.yml 中
    // @Value("${http.client.timeout.seconds:300}")
    private static final int HTTP_TIMEOUT_SECONDS = 300;

    @Bean // 声明这是一个 Bean 定义方法
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                // 在这里可以添加更多的配置，例如：
                // .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES)) // 配置连接池
                // .addInterceptor(new LoggingInterceptor()) // 添加日志拦截器等
                .build();
    }
}