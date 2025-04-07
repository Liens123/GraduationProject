package org.example.graduation_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.graduation_project.model.AdminUser;

@Mapper
public interface LoginMapper {
    /**
     * 通过用户名查找用户
     */
    AdminUser findByUsername(String username);

    /**
     * (新增) 插入一条用户记录
     */
    int insert(AdminUser user);

    /**
     * (新增) 更新用户记录
     */
    int update(AdminUser user);

    // 如果需要按主键查找、删除，也可添加：
    // AdminUser selectById(Long id);
    // int deleteById(Long id);
}
