package org.example.graduation_project.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 启用 Web 安全性
public class SecurityConfig {

    @Resource
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    // 定义公开访问的路径
    private static final String[] PUBLIC_PATHS = {
            "/api/v1/graduation/web/inner/login/password", // 登录接口
            "/api/v1/graduation/web/inner/login/register", //注册接口
            "/api/v1/graduation/web/inner/stats/**", //每日图表接口
            "/api/v1/graduation/web/inner/conversationLog/**", //分页查询接口
            "/v3/api-docs/**",               // OpenAPI v3 文档
            "/swagger-ui.html",             // Swagger UI 页面
            "/swagger-ui/**",               // Swagger UI 静态资源
            "/webjars/**"                   // Swagger UI 可能依赖的 Webjars
            // 如果有其他公开接口，也在这里添加
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 配置授权规则
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(PUBLIC_PATHS).permitAll() // 公开路径允许所有访问
                        .requestMatchers("/api/v1/graduation/web/inner/common/**").authenticated() // common 接口需要认证 (示例)
                        .requestMatchers("/api/v1/graduation/web/inner/login/**").authenticated()
                        .requestMatchers("/api/v1/graduation/web/inner/conversationLog/**").authenticated()
                        // 可以添加更多规则，例如基于角色的访问控制
                        // .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // 其他所有未匹配的请求都需要认证
                )
                // 2. 禁用 CSRF (对于无状态的 REST API 通常是安全的，如果使用 session 则需要开启)
                .csrf(AbstractHttpConfigurer::disable)
                // 3. 配置 Session 管理为无状态 (适用于 Token 认证)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 4. 禁用默认的 Form Login 和 HTTP Basic (因为我们将实现自定义登录逻辑或 Token 认证)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 5. 禁用默认的 Logout
                .logout(AbstractHttpConfigurer::disable);


        // 如果你后续要实现 Token 认证，会在这里添加自定义的 Filter
        // http.addFilterBefore(yourTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 定义密码编码器 Bean (非常重要！不要再用明文密码了)
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 BCrypt 强哈希算法
        return new BCryptPasswordEncoder();
    }
}
