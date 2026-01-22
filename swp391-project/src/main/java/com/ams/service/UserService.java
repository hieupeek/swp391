package com.ams.service;

import com.ams.model.User;

import java.util.Optional;

/**
 * Service Interface cho User.
 * Định nghĩa các nghiệp vụ liên quan đến quản lý và xác thực người dùng.
 */
public interface UserService {

    // ========== Authentication ==========

    /**
     * Xác thực người dùng bằng username và password.
     * 
     * @param username Tên đăng nhập
     * @param password Mật khẩu (plain text, sẽ được verify với BCrypt)
     * @return Optional chứa User nếu xác thực thành công, empty nếu thất bại
     */
    Optional<User> authenticate(String username, String password);

    /**
     * Đổi mật khẩu cho người dùng.
     * 
     * @param userId      ID của user
     * @param oldPassword Mật khẩu cũ (plain text)
     * @param newPassword Mật khẩu mới (plain text)
     * @return true nếu đổi mật khẩu thành công
     */
    boolean changePassword(Integer userId, String oldPassword, String newPassword);

    // ========== User Management ==========

    /**
     * Lấy User theo ID với đầy đủ thông tin Role và Department.
     * 
     * @param userId ID của user
     * @return Optional chứa User với Role/Department
     */
    Optional<User> getUserById(Integer userId);

    /**
     * Lấy User theo username.
     * 
     * @param username Tên đăng nhập
     * @return Optional chứa User nếu tìm thấy
     */
    Optional<User> getUserByUsername(String username);

    /**
     * Tạo người dùng mới.
     * Password sẽ được hash bằng BCrypt trước khi lưu.
     * 
     * @param user     User object (password ở dạng plain text)
     * @param password Password plain text
     * @return User đã được tạo (có ID)
     * @throws IllegalArgumentException nếu username hoặc email đã tồn tại
     */
    User createUser(User user, String password);

    /**
     * Cập nhật thông tin profile của người dùng.
     * Không bao gồm password và role.
     * 
     * @param user User với thông tin mới
     * @return true nếu cập nhật thành công
     */
    boolean updateProfile(User user);

    /**
     * Kiểm tra username đã tồn tại chưa.
     * 
     * @param username Username cần kiểm tra
     * @return true nếu đã tồn tại
     */
    boolean isUsernameExists(String username);

    /**
     * Kiểm tra email đã tồn tại chưa.
     * 
     * @param email Email cần kiểm tra
     * @return true nếu đã tồn tại
     */
    boolean isEmailExists(String email);
}
