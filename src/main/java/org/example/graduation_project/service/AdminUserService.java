package org.example.graduation_project.service;

import jakarta.annotation.Resource;
import org.example.graduation_project.dao.AdminUserDao;
import org.example.graduation_project.exception.BizException;
import org.example.graduation_project.model.AdminUser;
import org.springframework.stereotype.Service;


@Service
public class AdminUserService {

    @Resource
    private AdminUserDao adminUserDao;

    /**
     * 登录方法
     * @param username 用户名
     * @param password 密码
     * @return 成功返回用户对象，失败抛出BizException
     */
    public AdminUser login(String username, String password) {
        AdminUser user = adminUserDao.getByUsername(username);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new BizException("密码错误");
        }
        if (user.getStatus() != 1) {
            throw new BizException("账号已被禁用");
        }
        return user;
    }
}
