package com.ams.dao.impl;

import com.ams.dao.*;

import com.ams.model.Department;
import com.ams.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Triển khai JDBC cho DepartmentDAO.
 * Sử dụng PreparedStatement để tránh SQL Injection.
 */
public class DepartmentDAOImpl implements DepartmentDAO {

    // ========== SQL Queries ==========
    private static final String SQL_FIND_BY_ID = """
            SELECT dept_id, dept_name, description
            FROM departments
            WHERE dept_id = ?
            """;

    private static final String SQL_FIND_BY_NAME = """
            SELECT dept_id, dept_name, description
            FROM departments
            WHERE dept_name = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT dept_id, dept_name, description
            FROM departments
            ORDER BY dept_id
            """;

    private static final String SQL_INSERT = """
            INSERT INTO departments (dept_name, description)
            VALUES (?, ?)
            """;

    private static final String SQL_UPDATE = """
            UPDATE departments
            SET dept_name = ?, description = ?
            WHERE dept_id = ?
            """;

    private static final String SQL_DELETE = """
            DELETE FROM departments
            WHERE dept_id = ?
            """;

    @Override
    public Optional<Department> findById(Integer deptId) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setInt(1, deptId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToDepartment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Department> findByName(String deptName) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_NAME)) {

            ps.setString(1, deptName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToDepartment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                departments.add(mapResultSetToDepartment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    @Override
    public Department save(Department department) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, department.getDeptName());
            ps.setString(2, department.getDescription());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        department.setDeptId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public boolean update(Department department) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, department.getDeptName());
            ps.setString(2, department.getDescription());
            ps.setInt(3, department.getDeptId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer deptId) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, deptId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ========== Helper Methods ==========

    /**
     * Map ResultSet row sang Department object.
     * 
     * @param rs ResultSet đã trỏ đến row cần map
     * @return Department object
     * @throws SQLException nếu có lỗi đọc dữ liệu
     */
    private Department mapResultSetToDepartment(ResultSet rs) throws SQLException {
        return new Department(
                rs.getInt("dept_id"),
                rs.getString("dept_name"),
                rs.getString("description"));
    }
}
