package org.example.graduation_project.api.inner.resp.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.graduation_project.api.inner.req.RegistrationReq;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResp {
    @Schema
    private RegistrationReq req;
}
