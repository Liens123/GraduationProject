package org.example.graduation_project.controller;

import jakarta.annotation.Resource;
import org.example.graduation_project.api.Result;
import org.example.graduation_project.api.inner.LoginApi;
import org.example.graduation_project.api.inner.req.LoginByPasswordReq;
import org.example.graduation_project.api.inner.req.RegistrationReq;
import org.example.graduation_project.api.inner.resp.login.LoginByPasswordResp;
import org.example.graduation_project.service.login.impl.LoginServiceImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUserController implements LoginApi {

    @Resource
    private LoginServiceImpl loginService;

    @Override
    public Result<LoginByPasswordResp> loginByPassword(LoginByPasswordReq req) {
        return Result.success(loginService.login(req));
    }

    @Override
    public Result<Void> register(RegistrationReq registrationReq) {
        return null;
    }
}
