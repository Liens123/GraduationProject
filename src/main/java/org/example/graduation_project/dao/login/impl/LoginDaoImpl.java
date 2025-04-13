package org.example.graduation_project.dao.login.impl;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.example.graduation_project.dao.login.LoginDao;
import org.example.graduation_project.mapper.LoginMapper;
import org.example.graduation_project.model.AdminUser;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * DAO实现类，用MyBatis的Mapper来封装CRUD
 */
@Repository
@RequiredArgsConstructor
public class LoginDaoImpl implements LoginDao {

    @Resource
    private LoginMapper loginMapper;

    @Override
    public int save(AdminUser adminUser) {
        // 设置默认状态和时间 (如果Service层没设置)
        if (adminUser.getStatus() == null) {
            adminUser.setStatus(1); // Default to active
        }
        Date now = new Date();
        if (adminUser.getCreatedTime() == null) {
            adminUser.setCreatedTime(now);
        }
        if (adminUser.getUpdatedTime() == null) {
            adminUser.setUpdatedTime(now);
        }
        // 确保 operator 字段有值，如果需要的话
         if (adminUser.getOperator() == null) {
             adminUser.setOperator("李博宇"); // Or use username if passed in
         }
        return loginMapper.insert(adminUser);
    }

    @Override
    public int updatePassword(Long userId, String newEncodedPassword) {
        return loginMapper.updatePasswordByID(userId, newEncodedPassword, new Date());
    }

    @Override
    public AdminUser getByUsername(String username) {
        // 直接调用mapper
        return loginMapper.findByUsername(username);
    }

}
