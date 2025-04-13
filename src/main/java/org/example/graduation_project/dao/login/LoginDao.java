package org.example.graduation_project.dao.login;

import org.example.graduation_project.model.AdminUser;

public interface LoginDao {
    /**
     * @param adminUser
     * @return
     */
    int save(AdminUser adminUser);

    /**
     * @param id
     * @param newEncodedPassword
     * @return
     */
    int updatePassword(Long id, String newEncodedPassword);

    /**
     * @param username
     * @return
     */
    AdminUser getByUsername(String username);
}
