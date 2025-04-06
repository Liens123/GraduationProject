package org.example.graduation_project.dao;

import org.example.graduation_project.model.AdminUser;

public interface AdminUserDao {
    /**
     * @param adminUser
     * @return
     */
    int save(AdminUser adminUser);

    /**
     * @param adminUser
     * @return
     */
    int update(AdminUser adminUser);

    /**
     * @param username
     * @return
     */
    AdminUser getByUsername(String username);
}
