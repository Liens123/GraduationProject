package org.example.graduation_project.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // 这个 RedisTemplate<String, Object> 配置用于复杂的对象序列化。
    // 如果你只需要存储字符串（例如用于 Token 的用户名），
    // 可能只需要 Spring Boot 自动配置的 StringRedisTemplate 就够了。
    // 但配置了这个 Bean，以备将来可能需要。
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            @Qualifier("masterLettuceConnectionFactory") LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = buildRedisTemplate(connectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private RedisTemplate<String, Object> buildRedisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 使用 BasicPolymorphicTypeValidator 通常比 enableDefaultTyping 更安全
        objectMapper.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build(),
                ObjectMapper.DefaultTyping.NON_FINAL
        );

        Jackson2JsonRedisSerializer<Object> jacksonSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jacksonSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jacksonSerializer);
        return redisTemplate;
    }

    @Bean
    @Primary // 如果存在多个连接工厂，将此标记为主要的
    public LettuceConnectionFactory masterLettuceConnectionFactory(
            // 修正路径以匹配 application.yml
            @Value("${spring.data.redis.host}") String host,
            @Value("${spring.data.redis.port}") int port,
            @Value("${spring.data.redis.password:}") String password, // 处理空密码情况
            @Value("${spring.data.redis.database:0}") int database) {

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        if (password != null && !password.isEmpty()) {
            config.setPassword(password);
        }
        config.setDatabase(database);
        // 如果需要，可以在这里使用 LettuceClientConfiguration 添加超时等配置
        return new LettuceConnectionFactory(config);
    }
}
