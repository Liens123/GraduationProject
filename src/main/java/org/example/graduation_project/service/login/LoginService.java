package org.example.graduation_project.service.login;

import org.example.graduation_project.api.inner.req.LoginRequest;
import org.example.graduation_project.api.inner.resp.login.LoginByPasswordResp;

/**
 * 后台用户相关业务逻辑接口
 */
public interface LoginService {

    /**
     *
     * @param req
     * @return
     */
    LoginByPasswordResp login(LoginRequest req);
}
