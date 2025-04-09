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
        // 1.（权限校验）确保用户已登录 – 可通过现有登录模块获取当前用户
        //    例如从会话或token中提取用户ID，如果未登录则抛出异常
//        Long userId = getCurrentUserIdFromSessionOrToken();
//        if (userId == null) {
//            throw new BizException("用户未登录");  // 会被全局异常处理为 A00002 或 A00003 错误码
//        }
        long userId = 1;

        // 2. 调用服务层处理文件上传和数据导入
        int importedCount = commonService.importExcelData(file, userId);

        // 3. 返回统一成功结果，附带导入条数
        return Result.success(importedCount);
    }
}
