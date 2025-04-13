package org.example.graduation_project.service.login;

import org.example.graduation_project.api.inner.req.LoginByPasswordReq;
import org.example.graduation_project.api.inner.req.RegistrationReq;
import org.example.graduation_project.api.inner.req.UpdatePasswordReq;
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
    LoginByPasswordResp login(LoginByPasswordReq req);


    /**
     *
     * @param req
     */
    void register(RegistrationReq req);

    void updatePassword(String userName,UpdatePasswordReq req);

    void logout(String token);
}