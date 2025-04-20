package org.example.graduation_project.api.inner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.graduation_project.api.Result;
import org.example.graduation_project.api.constant.InnerApiConstant;
import org.example.graduation_project.api.inner.req.LoginByPasswordReq;
import org.example.graduation_project.api.inner.req.RegistrationReq;
import org.example.graduation_project.api.inner.req.UpdatePasswordReq;
import org.example.graduation_project.api.inner.resp.login.LoginByPasswordResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


@Tag(name = "用户认证接口", description = "账号密码登录 + 用户注册 + 修改密码 + 退出登录")
@RequestMapping(InnerApiConstant.INNER_LOGIN_API)
public interface LoginApi {

    @Operation(summary = "账号登录")
    @PostMapping("/password")
    Result<LoginByPasswordResp> loginByPassword(@RequestBody @Valid LoginByPasswordReq loginByPasswordReq);

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    Result<Void> register(@RequestBody @Valid RegistrationReq registrationReq); // Use Void for simple success/fail

    @Operation(summary = "修改密码")
    @PostMapping("/updatePassword")
    Result<Void> updatePassword(@RequestBody @Valid UpdatePasswordReq updatePasswordReq);

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    Result<Void> logout(HttpServletRequest request);
}
