package org.example.graduation_project.service.login.impl;

import jakarta.annotation.Resource;
import org.example.graduation_project.api.inner.req.LoginByPasswordReq;
import org.example.graduation_project.api.inner.req.RegistrationReq;
import org.example.graduation_project.api.inner.resp.login.LoginByPasswordResp;
import org.example.graduation_project.dao.login.LoginDao;
import org.example.graduation_project.exception.BizException;
import org.example.graduation_project.model.AdminUser;
import org.example.graduation_project.service.login.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;


@Service
public class LoginServiceImpl implements LoginService {

    @Value("${app.security.access-token.redis-key-prefix:login:token:}") // Provide default value
    private String redisKeyPrefix;

    @Value("${app.security.access-token.expire-time-seconds:3600}") // Provide default value
    private long expireTimeSeconds;

    @Resource
    private LoginDao loginDao;
    @Resource
    private StringRedisTemplate stringRedisTemplate;



    /**
     *
     * @param req
     * @return token,user
     */
    @Override
    public LoginByPasswordResp login(LoginByPasswordReq req) {
        AdminUser user = loginDao.getByUsername(req.getUsername());
        if (user == null) {
            throw new BizException("用户不存在");
        }
        if (!user.getPassword().equals(req.getPassword())) {
            throw new BizException("密码错误");
        }
        if (user.getStatus() != 1) {
            throw new BizException("账号已被禁用");
        }
        // 生成 accessToken
        String accessToken = UUID.randomUUID().toString();
        String redisKey = "login:token:" + accessToken;

        // 存储 accessToken 到 Redis，默认有效期 1 小时
        stringRedisTemplate.opsForValue().set(redisKey, user.getUsername(), Duration.ofHours(1));

        // 返回响应
        return new LoginByPasswordResp(accessToken);
    }

    @Override
    @Transactional
    public void register(RegistrationReq req) {
        AdminUser existingUser = loginDao.getByUsername(req.getUsername());
        if (existingUser != null) {
            throw new BizException("用户名 '" + req.getUsername() + "' 已被注册");
        }

        AdminUser newUser = new AdminUser();
        newUser.setName(req.getUsername().trim());
        newUser.setPassword(req.getPassword().trim());
        newUser.setName(req.getName() != null ? req.getName().trim() : null);
        newUser.setStatus(1);

        int result = loginDao.save(newUser);

        if (result <= 0) {
            // 这个情况通常是数据库层面的问题，会被 Spring 的 DataAccessException 捕获
            // 但也可以加一层检查以防万一
            throw new BizException("用户注册失败，请稍后重试");
        }
        // 注册成功，不需要返回特定内容
    }
}