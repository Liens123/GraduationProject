package org.example.graduation_project.api.inner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.graduation_project.api.Result;
import org.example.graduation_project.api.constant.InnerApiConstant;
import org.example.graduation_project.api.inner.req.LoginByPasswordReq;
import org.example.graduation_project.api.inner.req.RegistrationReq;
import org.example.graduation_project.api.inner.resp.login.LoginByPasswordResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Tag(name = "登录接口", description = "账号密码登录 + 查询用户 + 退出登录")
@RequestMapping(InnerApiConstant.INNER_LOGIN_API)
public interface LoginApi {

    @Operation(summary = "账号登录")
    @PostMapping("/password")
    Result<LoginByPasswordResp> loginByPassword(@RequestBody @Valid LoginByPasswordReq loginByPasswordReq);

    @Operation(summary = "用户注册")
    @PostMapping("/register") // Define new endpoint path
    Result<Void> register(@RequestBody @Valid RegistrationReq registrationReq); // Use Void for simple success/fail
}

