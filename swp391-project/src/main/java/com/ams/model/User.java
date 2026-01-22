package com.ams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Model class đại diện cho người dùng trong hệ thống.
 * Tương ứng với bảng 'users' trong database.
 * 
 * Mỗi user thuộc một Role và có thể thuộc một Department.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * ID của người dùng (Primary Key, Auto Increment).
     */
    private Integer userId;

    /**
     * Tên đăng nhập (Unique, Not Null).
     */
    private String username;

    /**
     * Mật khẩu đã được hash bằng BCrypt.
     * KHÔNG BAO GIỜ lưu plain text password.
     */
    private String passwordHash;

    /**
     * Họ tên đầy đủ của người dùng.
     */
    private String fullName;

    /**
     * Email (Unique, Not Null).
     */
    private String email;

    /**
     * Số điện thoại (Optional).
     */
    private String phone;

    /**
     * ID của vai trò (Foreign Key đến bảng roles).
     */
    private Integer roleId;

    /**
     * ID của phòng ban (Foreign Key đến bảng departments).
     * Có thể null nếu người dùng không thuộc phòng ban cụ thể.
     */
    private Integer deptId;

    /**
     * Trạng thái của người dùng (Active/Inactive).
     */
    private UserStatus status;

    /**
     * Thời điểm tạo tài khoản.
     */
    private LocalDateTime createdAt;

    // ========== Transient Fields (không lưu trong DB, dùng cho join) ==========

    /**
     * Đối tượng Role đầy đủ (populated khi join với bảng roles).
     */
    private Role role;

    /**
     * Đối tượng Department đầy đủ (populated khi join với bảng departments).
     */
    private Department department;

    /**
     * Constructor tạo User mới để đăng ký (không có ID, createdAt).
     * 
     * @param username     Tên đăng nhập
     * @param passwordHash Mật khẩu đã hash
     * @param fullName     Họ tên
     * @param email        Email
     * @param phone        Số điện thoại
     * @param roleId       ID vai trò
     * @param deptId       ID phòng ban
     */
    public User(String username, String passwordHash, String fullName,
            String email, String phone, Integer roleId, Integer deptId) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.roleId = roleId;
        this.deptId = deptId;
        this.status = UserStatus.Active; // Mặc định Active khi tạo mới
    }
}
