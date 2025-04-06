package org.example.graduation_project.dao.impl;

import jakarta.annotation.Resource;
import org.example.graduation_project.dao.AdminUserDao;
import org.example.graduation_project.mapper.AdminUserMapper;
import org.example.graduation_project.model.AdminUser;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * DAO实现类，用MyBatis的Mapper来封装CRUD
 */
@Repository
public class AdminUserDaoImpl implements AdminUserDao {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public int save(AdminUser adminUser) {
        // 设置默认状态和时间
        adminUser.setStatus(adminUser.getStatus() == null ? 1 : adminUser.getStatus());
        adminUser.setCreatedTime(new Date());
        adminUser.setUpdatedTime(new Date());
        return adminUserMapper.insert(adminUser);
    }

    @Override
    public int update(AdminUser adminUser) {
        adminUser.setUpdatedTime(new Date());
        return adminUserMapper.update(adminUser);
    }

    @Override
    public AdminUser getByUsername(String username) {
        // 直接调用mapper
        return adminUserMapper.findByUsername(username);
    }
}
