package org.example.graduation_project.service.login.impl;

import com.alibaba.excel.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.graduation_project.api.inner.req.LoginByPasswordReq;
import org.example.graduation_project.api.inner.req.RegistrationReq;
import org.example.graduation_project.api.inner.req.UpdatePasswordReq;
import org.example.graduation_project.api.inner.resp.login.LoginByPasswordResp;
import org.example.graduation_project.dao.login.LoginDao;
import org.example.graduation_project.exception.BizException;
import org.example.graduation_project.model.AdminUser;
import org.example.graduation_project.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
//    private static final String redisUserKeyPrefix = "login:user:";

    @Value("${app.security.access-token.redis-key-prefix:login:user:}")
    private String redisUserKeyPrefix;

    @Value("${app.security.access-token.redis-key-prefix:login:token:}") // Provide default value
    private String redisKeyPrefix;

    @Value("${app.security.access-token.expire-time-seconds:3600}") // Provide default value
    private long expireTimeSeconds;

    @Resource
    private LoginDao loginDao;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;


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
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BizException("密码错误");
        }
        if (user.getStatus() != 1) {
            throw new BizException("账号已被禁用");
        }
        // 检查该用户是否已有 Token（即已登录）
        String userKey = redisUserKeyPrefix + user.getUsername();
        String existingToken = stringRedisTemplate.opsForValue().get(userKey);
        if (existingToken != null) {
            throw new BizException("用户已登录，请勿重复登录");
        }
        // 生成 accessToken
        String accessToken = UUID.randomUUID().toString();
        String redisKey = redisKeyPrefix + accessToken;

        // 存储 accessToken 到 Redis，默认有效期 1 小时
        stringRedisTemplate.opsForValue().set(redisKey, user.getUsername(), Duration.ofSeconds(expireTimeSeconds));

        // 写入 Redis（双向）
        stringRedisTemplate.opsForValue().set(userKey, accessToken, Duration.ofSeconds(expireTimeSeconds));

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


        if(req.getUsername() != null) {
            newUser.setUsername(req.getUsername().trim());
        }

        if(req.getPassword() != null) {
            newUser.setPassword(passwordEncoder.encode(req.getPassword()).trim());
        }

        if(req.getUsername() != null) {
            newUser.setName(req.getName().trim());
        }
        newUser.setStatus(1);

        int result = loginDao.save(newUser);

        if (result <= 0) {
            // 这个情况通常是数据库层面的问题，会被 Spring 的 DataAccessException 捕获
            // 但也可以加一层检查以防万一
            throw new BizException("用户注册失败，请稍后重试");
        }
        // 注册成功，不需要返回特定内容
    }

    @Override
    @Transactional
    public void updatePassword(String userName,UpdatePasswordReq req) {

        //根据用户名获取用户信息
        AdminUser user = loginDao.getByUsername(userName);

        if(user == null) {
            throw new BizException("获取用户信息失败");
        }

        if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
            throw new BizException("当前密码输入错误");
        }

        if (passwordEncoder.matches(req.getNewPassword(), user.getPassword())) {
            throw new BizException("新密码不能与原密码相同");
        }

        //新密码加密
        String encryptedNewPassword = passwordEncoder.encode(req.getNewPassword());

        user.setPassword(encryptedNewPassword);

        int result =loginDao.updatePassword(user.getId(),encryptedNewPassword);

        if (result <= 0) {
            // 更新失败，可能是并发问题或数据库错误
            throw new BizException("密码更新失败，请稍后重试");
        }

        // 6. (可选) 使旧的登录 Token 失效
    }

    @Override
    public void logout(String token) {
        if (StringUtils.isNotBlank(token)) {
            String tokenKey = redisKeyPrefix + token;

            // 获取 username
            String username = stringRedisTemplate.opsForValue().get(tokenKey);

            // 删除 token -> username 映射
            stringRedisTemplate.delete(tokenKey);

            // 删除 username -> token 映射
            if (StringUtils.isNotBlank(username)) {
                stringRedisTemplate.delete(redisUserKeyPrefix + username);
            }
        }
    }

}