package com.ams.dao.impl;

import com.ams.dao.*;

import com.ams.model.Department;
import com.ams.model.Role;
import com.ams.model.User;
import com.ams.model.UserStatus;
import com.ams.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Triển khai JDBC cho UserDAO.
 * Sử dụng PreparedStatement để tránh SQL Injection.
 */
public class UserDAOImpl implements UserDAO {

    // ========== SQL Queries ==========
    private static final String SQL_FIND_BY_ID = """
            SELECT user_id, username, password_hash, full_name, email, phone,
                   role_id, dept_id, status, created_at
            FROM users
            WHERE user_id = ?
            """;

    private static final String SQL_FIND_BY_USERNAME = """
            SELECT user_id, username, password_hash, full_name, email, phone,
                   role_id, dept_id, status, created_at
            FROM users
            WHERE username = ?
            """;

    private static final String SQL_FIND_BY_EMAIL = """
            SELECT user_id, username, password_hash, full_name, email, phone,
                   role_id, dept_id, status, created_at
            FROM users
            WHERE email = ?
            """;

    private static final String SQL_FIND_BY_ID_WITH_DETAILS = """
            SELECT u.user_id, u.username, u.password_hash, u.full_name, u.email, u.phone,
                   u.role_id, u.dept_id, u.status, u.created_at,
                   r.role_name, r.description AS role_desc,
                   d.dept_name, d.description AS dept_desc
            FROM users u
            INNER JOIN roles r ON u.role_id = r.role_id
            LEFT JOIN departments d ON u.dept_id = d.dept_id
            WHERE u.user_id = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT user_id, username, password_hash, full_name, email, phone,
                   role_id, dept_id, status, created_at
            FROM users
            ORDER BY user_id
            """;

    private static final String SQL_FIND_BY_ROLE = """
            SELECT user_id, username, password_hash, full_name, email, phone,
                   role_id, dept_id, status, created_at
            FROM users
            WHERE role_id = ?
            ORDER BY user_id
            """;

    private static final String SQL_FIND_BY_DEPT = """
            SELECT user_id, username, password_hash, full_name, email, phone,
                   role_id, dept_id, status, created_at
            FROM users
            WHERE dept_id = ?
            ORDER BY user_id
            """;

    private static final String SQL_FIND_BY_STATUS = """
            SELECT user_id, username, password_hash, full_name, email, phone,
                   role_id, dept_id, status, created_at
            FROM users
            WHERE status = ?
            ORDER BY user_id
            """;

    private static final String SQL_INSERT = """
            INSERT INTO users (username, password_hash, full_name, email, phone, role_id, dept_id, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_UPDATE = """
            UPDATE users
            SET full_name = ?, email = ?, phone = ?, role_id = ?, dept_id = ?
            WHERE user_id = ?
            """;

    private static final String SQL_UPDATE_PASSWORD = """
            UPDATE users
            SET password_hash = ?
            WHERE user_id = ?
            """;

    private static final String SQL_UPDATE_STATUS = """
            UPDATE users
            SET status = ?
            WHERE user_id = ?
            """;

    private static final String SQL_DELETE = """
            DELETE FROM users
            WHERE user_id = ?
            """;

    private static final String SQL_EXISTS_BY_USERNAME = """
            SELECT 1 FROM users WHERE username = ? LIMIT 1
            """;

    private static final String SQL_EXISTS_BY_EMAIL = """
            SELECT 1 FROM users WHERE email = ? LIMIT 1
            """;

    @Override
    public Optional<User> findById(Integer userId) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_USERNAME)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_EMAIL)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByIdWithDetails(Integer userId) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID_WITH_DETAILS)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = mapResultSetToUser(rs);

                    // Map Role
                    Role role = new Role(
                            rs.getInt("role_id"),
                            rs.getString("role_name"),
                            rs.getString("role_desc"));
                    user.setRole(role);

                    // Map Department (có thể null)
                    int deptId = rs.getInt("dept_id");
                    if (!rs.wasNull()) {
                        Department dept = new Department(
                                deptId,
                                rs.getString("dept_name"),
                                rs.getString("dept_desc"));
                        user.setDepartment(dept);
                    }

                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> findByRole(Integer roleId) {
        List<User> users = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ROLE)) {

            ps.setInt(1, roleId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> findByDepartment(Integer deptId) {
        List<User> users = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_DEPT)) {

            ps.setInt(1, deptId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> findByStatus(UserStatus status) {
        List<User> users = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_STATUS)) {

            ps.setString(1, status.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User user) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setInt(6, user.getRoleId());

            // dept_id có thể null
            if (user.getDeptId() != null) {
                ps.setInt(7, user.getDeptId());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            ps.setString(8, user.getStatus() != null ? user.getStatus().name() : UserStatus.Active.name());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean update(User user) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setInt(4, user.getRoleId());

            if (user.getDeptId() != null) {
                ps.setInt(5, user.getDeptId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.setInt(6, user.getUserId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePassword(Integer userId, String newPassword) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_PASSWORD)) {

            ps.setString(1, newPassword);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStatus(Integer userId, UserStatus status) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS)) {

            ps.setString(1, status.name());
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer userId) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_EXISTS_BY_USERNAME)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_EXISTS_BY_EMAIL)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ========== Helper Methods ==========

    /**
     * Map ResultSet row sang User object.
     * 
     * @param rs ResultSet đã trỏ đến row cần map
     * @return User object
     * @throws SQLException nếu có lỗi đọc dữ liệu
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setRoleId(rs.getInt("role_id"));

        // dept_id có thể null
        int deptId = rs.getInt("dept_id");
        user.setDeptId(rs.wasNull() ? null : deptId);

        // Parse status enum
        String statusStr = rs.getString("status");
        user.setStatus(statusStr != null ? UserStatus.valueOf(statusStr) : UserStatus.Active);

        // Parse timestamp
        Timestamp createdAt = rs.getTimestamp("created_at");
        user.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : null);

        return user;
    }
}
