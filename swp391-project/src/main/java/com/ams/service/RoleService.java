package com.ams.service;

import com.ams.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface cho Role.
 * Định nghĩa các nghiệp vụ liên quan đến quản lý vai trò.
 */
public interface RoleService {

    /**
     * Lấy Role theo ID.
     * 
     * @param roleId ID của role
     * @return Optional chứa Role nếu tìm thấy
     */
    Optional<Role> getRoleById(Integer roleId);

    /**
     * Lấy Role theo tên.
     * 
     * @param roleName Tên role
     * @return Optional chứa Role nếu tìm thấy
     */
    Optional<Role> getRoleByName(String roleName);

    /**
     * Lấy tất cả các Role trong hệ thống.
     * 
     * @return Danh sách Role
     */
    List<Role> getAllRoles();
}
