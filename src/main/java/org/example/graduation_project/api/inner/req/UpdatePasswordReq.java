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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户修改密码请求")
public class UpdatePasswordReq {

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 30, message = "密码长度必须在 6 到 30 个字符之间")
    @Schema(description = "密码", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 30, message = "密码长度必须在 6 到 30 个字符之间")
    @Schema(description = "新密码", example = "123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;
}
