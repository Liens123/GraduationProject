package org.example.graduation_project.api.inner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.graduation_project.api.Result;
import org.example.graduation_project.api.constant.InnerApiConstant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "公共接口" , description = "提供文件上传等通用功能")
@RequestMapping(InnerApiConstant.INNER_COMMON_API)
public interface CommonApi {
    @Operation(summary = "文件上传")
    @PostMapping("uploadExcel")
    Result<Integer> uploadExcel(@RequestParam("file") MultipartFile file);
}