package org.example.graduation_project.api.inner.resp.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.graduation_project.model.AdminUser;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginByPasswordResp {
    @Schema(description = "请求令牌")
    private String accessToken;
    @Schema(description = "请求内容")
    private AdminUser adminUser;
}
