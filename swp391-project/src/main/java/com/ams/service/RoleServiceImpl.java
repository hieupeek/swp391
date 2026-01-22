package com.ams.service;

import com.ams.dao.RoleDAO;
import com.ams.dao.RoleDAOImpl;
import com.ams.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai RoleService.
 * Xử lý nghiệp vụ liên quan đến quản lý vai trò.
 */
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    /**
     * Constructor mặc định - sử dụng RoleDAOImpl.
     */
    public RoleServiceImpl() {
        this.roleDAO = new RoleDAOImpl();
    }

    /**
     * Constructor với dependency injection.
     * 
     * @param roleDAO DAO instance để inject
     */
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public Optional<Role> getRoleById(Integer roleId) {
        if (roleId == null || roleId <= 0) {
            return Optional.empty();
        }
        return roleDAO.findById(roleId);
    }

    @Override
    public Optional<Role> getRoleByName(String roleName) {
        if (roleName == null || roleName.isBlank()) {
            return Optional.empty();
        }
        return roleDAO.findByName(roleName.trim());
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }
}
