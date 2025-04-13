package org.example.graduation_project.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.example.graduation_project.api.Result;
import org.example.graduation_project.api.inner.LoginApi;
import org.example.graduation_project.api.inner.req.LoginByPasswordReq;
import org.example.graduation_project.api.inner.req.RegistrationReq;
import org.example.graduation_project.api.inner.req.UpdatePasswordReq;
import org.example.graduation_project.api.inner.resp.login.LoginByPasswordResp;
import org.example.graduation_project.service.login.impl.LoginServiceImpl;
import org.example.graduation_project.service.stats.ConversationStatsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUserController implements LoginApi {

    @Resource
    private LoginServiceImpl loginService;

    @Resource
    private ConversationStatsService conversationStatsService;

    @Override
    public Result<LoginByPasswordResp> loginByPassword(LoginByPasswordReq req) {

//        String dateTimeStr = "2024-12-15 19:17:44";
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//// 1) 先把字符串解析为 LocalDateTime
//        LocalDateTime ldt = LocalDateTime.parse(dateTimeStr, dtf);
//// 2) 再转换为 LocalDate（只保留年月日）
//        LocalDate date = ldt.toLocalDate();
//        conversationStatsService.generateAndSaveDailyStats(date);
//
//
//        String dateStr = "2024-12-15 00:00:00";
//        LocalDate date1 = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
//        conversationStatsService.generateAndSaveHourlyStats(date1);
        return Result.success(loginService.login(req));
    }

    @Override
    public Result<Void> register(RegistrationReq req) {
        loginService.register(req);
        return Result.success();
    }

    @Override
    public Result<Void> updatePassword(UpdatePasswordReq req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return Result.error(Result.ResultCodeEnum.A00002);
        }
        String currentUserName = authentication.getName();
        loginService.updatePassword(currentUserName,req);
        return Result.success();
    }

    @Override
    public Result<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            loginService.logout(token);
        }
        return Result.success();
    }

}
