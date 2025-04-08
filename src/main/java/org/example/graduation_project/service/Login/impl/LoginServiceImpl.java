package org.example.graduation_project.service.Login.impl;

import jakarta.annotation.Resource;
import org.example.graduation_project.api.inner.req.LoginRequest;
import org.example.graduation_project.api.inner.resp.login.LoginByPasswordResp;
import org.example.graduation_project.dao.LoginDao;
import org.example.graduation_project.exception.BizException;
import org.example.graduation_project.model.AdminUser;
import org.example.graduation_project.service.Login.LoginService;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginDao loginDao;

    /**
     *
     * @param req
     * @return token,user
     */
    @Override
    public LoginByPasswordResp login(LoginRequest req) {
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
        String token = UUID.randomUUID().toString();
        return new LoginByPasswordResp(token, user);
    }
}