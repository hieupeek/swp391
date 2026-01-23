package com.ams.dao.impl;

import com.ams.dao.*;

import com.ams.model.Role;
import com.ams.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Triển khai JDBC cho RoleDAO.
 * Sử dụng PreparedStatement để tránh SQL Injection.
 */
public class RoleDAOImpl implements RoleDAO {

    // ========== SQL Queries ==========
    private static final String SQL_FIND_BY_ID = """
            SELECT role_id, role_name, description
            FROM roles
            WHERE role_id = ?
            """;

    private static final String SQL_FIND_BY_NAME = """
            SELECT role_id, role_name, description
            FROM roles
            WHERE role_name = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT role_id, role_name, description
            FROM roles
            ORDER BY role_id
            """;

    private static final String SQL_INSERT = """
            INSERT INTO roles (role_name, description)
            VALUES (?, ?)
            """;

    private static final String SQL_UPDATE = """
            UPDATE roles
            SET role_name = ?, description = ?
            WHERE role_id = ?
            """;

    private static final String SQL_DELETE = """
            DELETE FROM roles
            WHERE role_id = ?
            """;

    @Override
    public Optional<Role> findById(Integer roleId) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setInt(1, roleId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToRole(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Role> findByName(String roleName) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_NAME)) {

            ps.setString(1, roleName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToRole(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                roles.add(mapResultSetToRole(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public Role save(Role role) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, role.getRoleName());
            ps.setString(2, role.getDescription());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        role.setRoleId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public boolean update(Role role) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, role.getRoleName());
            ps.setString(2, role.getDescription());
            ps.setInt(3, role.getRoleId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer roleId) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, roleId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ========== Helper Methods ==========

    /**
     * Map ResultSet row sang Role object.
     * 
     * @param rs ResultSet đã trỏ đến row cần map
     * @return Role object
     * @throws SQLException nếu có lỗi đọc dữ liệu
     */
    private Role mapResultSetToRole(ResultSet rs) throws SQLException {
        return new Role(
                rs.getInt("role_id"),
                rs.getString("role_name"),
                rs.getString("description"));
    }
}
