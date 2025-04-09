package org.example.graduation_project.dao.login.impl;

import jakarta.annotation.Resource;
import org.example.graduation_project.dao.login.LoginDao;
import org.example.graduation_project.mapper.LoginMapper;
import org.example.graduation_project.model.AdminUser;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * DAO实现类，用MyBatis的Mapper来封装CRUD
 */
@Repository
public class LoginDaoImpl implements LoginDao {

    @Resource
    private LoginMapper loginMapper;

    @Override
    public int save(AdminUser adminUser) {
        // 设置默认状态和时间
        adminUser.setStatus(adminUser.getStatus() == null ? 1 : adminUser.getStatus());
        adminUser.setCreatedTime(new Date());
        adminUser.setUpdatedTime(new Date());
        return loginMapper.insert(adminUser);
    }

    @Override
    public int update(AdminUser adminUser) {
        adminUser.setUpdatedTime(new Date());
        return loginMapper.update(adminUser);
    }

    @Override
    public AdminUser getByUsername(String username) {
        // 直接调用mapper
        return loginMapper.findByUsername(username);
    }
}
