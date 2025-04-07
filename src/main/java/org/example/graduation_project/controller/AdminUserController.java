package org.example.graduation_project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.graduation_project.api.Result;
import org.example.graduation_project.api.inner.req.LoginRequest;
import org.example.graduation_project.api.inner.LoginApi;
import org.example.graduation_project.api.inner.resp.login.LoginByPasswordResp;
import org.example.graduation_project.service.Login.impl.LoginServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "后台登录接口", description = "用户登录与令牌管理")
public class AdminUserController implements LoginApi {

    @Resource
    private LoginServiceImpl loginService;

    @Override
    public Result<LoginByPasswordResp> loginByPassword(LoginRequest req) {
        return Result.success(loginService.login(req));
    }
}
