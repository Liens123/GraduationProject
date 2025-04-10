package org.example.graduation_project.controller;

import jakarta.annotation.Resource;
import org.example.graduation_project.api.Result;
import org.example.graduation_project.api.inner.CommonApi;
import org.example.graduation_project.api.inner.LoginApi;
import org.example.graduation_project.exception.BizException;
import org.example.graduation_project.service.common.CommonService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CommonController implements CommonApi {

    @Resource
    private CommonService commonService;

    @Override
    public Result<Integer> uploadExcel(MultipartFile file) {
        // 模拟用户 ID（实际应从登录状态中获取）
        long userId = 1;

        int count = commonService.importExcelData(file, userId);

        return Result.success(count);
    }
}
