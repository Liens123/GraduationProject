package org.example.graduation_project.api.inner.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户注册请求")
public class RegistrationReq {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 20, message = "用户名长度必须在 4 到 20 个字符之间")
    @Schema(description = "用户名", example = "newuser", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 30, message = "密码长度必须在 6 到 30 个字符之间")
    @Schema(description = "密码", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    // 可以添加其他字段，例如 'name'
    @Schema(description = "用户姓名（可选）", example = "张三")
    private String name; // 可选字段

    // Lombok 会生成 Getters/Setters
}
