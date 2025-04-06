package org.example.graduation_project.controller;

import jakarta.validation.Valid;
import org.example.graduation_project.api.inner.req.LoginRequest;
import org.example.graduation_project.api.inner.resp.ApiResponse;
import org.example.graduation_project.model.AdminUser;
import org.example.graduation_project.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 用户登录接口
     */
    @PostMapping("/login")
    public ApiResponse<AdminUser> login(@RequestBody @Valid LoginRequest request) {
        AdminUser user = adminUserService.login(request.getUsername(), request.getPassword());
        return ApiResponse.success(user);
    }
}
