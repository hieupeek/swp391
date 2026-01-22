package com.ams.dao;

import com.ams.model.Department;

import java.util.List;
import java.util.Optional;

/**
 * DAO Interface cho Department entity.
 * Định nghĩa các phương thức truy cập dữ liệu cho bảng departments.
 */
public interface DepartmentDAO {

    /**
     * Tìm Department theo ID.
     * 
     * @param deptId ID của department cần tìm
     * @return Optional chứa Department nếu tìm thấy, empty nếu không
     */
    Optional<Department> findById(Integer deptId);

    /**
     * Tìm Department theo tên.
     * 
     * @param deptName Tên department cần tìm
     * @return Optional chứa Department nếu tìm thấy, empty nếu không
     */
    Optional<Department> findByName(String deptName);

    /**
     * Lấy danh sách tất cả các Department.
     * 
     * @return Danh sách Department, rỗng nếu không có
     */
    List<Department> findAll();

    /**
     * Thêm Department mới vào database.
     * 
     * @param department Department cần thêm (không có ID)
     * @return Department đã được thêm (có ID)
     */
    Department save(Department department);

    /**
     * Cập nhật thông tin Department.
     * 
     * @param department Department cần cập nhật (phải có ID)
     * @return true nếu cập nhật thành công, false nếu không tìm thấy
     */
    boolean update(Department department);

    /**
     * Xóa Department theo ID.
     * 
     * @param deptId ID của department cần xóa
     * @return true nếu xóa thành công, false nếu không tìm thấy
     */
    boolean delete(Integer deptId);
}
