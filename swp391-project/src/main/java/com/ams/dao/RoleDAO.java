package com.ams.dao;

import com.ams.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * DAO Interface cho Role entity.
 * Định nghĩa các phương thức truy cập dữ liệu cho bảng roles.
 */
public interface RoleDAO {

    /**
     * Tìm Role theo ID.
     * 
     * @param roleId ID của role cần tìm
     * @return Optional chứa Role nếu tìm thấy, empty nếu không
     */
    Optional<Role> findById(Integer roleId);

    /**
     * Tìm Role theo tên.
     * 
     * @param roleName Tên role cần tìm (VD: "Principal", "Finance_Head")
     * @return Optional chứa Role nếu tìm thấy, empty nếu không
     */
    Optional<Role> findByName(String roleName);

    /**
     * Lấy danh sách tất cả các Role.
     * 
     * @return Danh sách Role, rỗng nếu không có
     */
    List<Role> findAll();

    /**
     * Thêm Role mới vào database.
     * 
     * @param role Role cần thêm (không có ID)
     * @return Role đã được thêm (có ID)
     */
    Role save(Role role);

    /**
     * Cập nhật thông tin Role.
     * 
     * @param role Role cần cập nhật (phải có ID)
     * @return true nếu cập nhật thành công, false nếu không tìm thấy
     */
    boolean update(Role role);

    /**
     * Xóa Role theo ID.
     * 
     * @param roleId ID của role cần xóa
     * @return true nếu xóa thành công, false nếu không tìm thấy
     */
    boolean delete(Integer roleId);
}
