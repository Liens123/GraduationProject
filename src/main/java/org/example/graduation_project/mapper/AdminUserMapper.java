package org.example.graduation_project.mapper;

import org.example.graduation_project.model.AdminUser;

public interface AdminUserMapper {
    AdminUser findByUsername(String username);
}
