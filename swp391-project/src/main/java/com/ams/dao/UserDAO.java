package com.ams.dao;

import com.ams.model.User;
import com.ams.model.UserStatus;

import java.util.List;
import java.util.Optional;

/**
 * DAO Interface cho User entity.
 * Định nghĩa các phương thức truy cập dữ liệu cho bảng users.
 */
public interface UserDAO {

    /**
     * Tìm User theo ID.
     * 
     * @param userId ID của user cần tìm
     * @return Optional chứa User nếu tìm thấy, empty nếu không
     */
    Optional<User> findById(Integer userId);

    /**
     * Tìm User theo username (dùng cho đăng nhập).
     * 
     * @param username Username cần tìm
     * @return Optional chứa User nếu tìm thấy, empty nếu không
     */
    Optional<User> findByUsername(String username);

    /**
     * Tìm User theo email.
     * 
     * @param email Email cần tìm
     * @return Optional chứa User nếu tìm thấy, empty nếu không
     */
    Optional<User> findByEmail(String email);

    /**
     * Tìm User theo ID với đầy đủ thông tin Role và Department.
     * 
     * @param userId ID của user cần tìm
     * @return Optional chứa User (với Role, Department) nếu tìm thấy
     */
    Optional<User> findByIdWithDetails(Integer userId);

    /**
     * Lấy danh sách tất cả các User.
     * 
     * @return Danh sách User, rỗng nếu không có
     */
    List<User> findAll();

    /**
     * Lấy danh sách User theo Role.
     * 
     * @param roleId ID của role
     * @return Danh sách User có role tương ứng
     */
    List<User> findByRole(Integer roleId);

    /**
     * Lấy danh sách User theo Department.
     * 
     * @param deptId ID của department
     * @return Danh sách User thuộc department tương ứng
     */
    List<User> findByDepartment(Integer deptId);

    /**
     * Lấy danh sách User theo Status.
     * 
     * @param status Trạng thái cần lọc
     * @return Danh sách User có status tương ứng
     */
    List<User> findByStatus(UserStatus status);

    /**
     * Thêm User mới vào database.
     * 
     * @param user User cần thêm (không có ID)
     * @return User đã được thêm (có ID)
     */
    User save(User user);

    /**
     * Cập nhật thông tin User (không bao gồm password).
     * 
     * @param user User cần cập nhật (phải có ID)
     * @return true nếu cập nhật thành công, false nếu không tìm thấy
     */
    boolean update(User user);

    /**
     * Cập nhật password của User.
     * 
     * @param userId      ID của user
     * @param newPassword Password mới đã được hash bằng BCrypt
     * @return true nếu cập nhật thành công
     */
    boolean updatePassword(Integer userId, String newPassword);

    /**
     * Cập nhật status của User.
     * 
     * @param userId ID của user
     * @param status Status mới
     * @return true nếu cập nhật thành công
     */
    boolean updateStatus(Integer userId, UserStatus status);

    /**
     * Xóa User theo ID.
     * 
     * @param userId ID của user cần xóa
     * @return true nếu xóa thành công, false nếu không tìm thấy
     */
    boolean delete(Integer userId);

    /**
     * Kiểm tra username đã tồn tại chưa.
     * 
     * @param username Username cần kiểm tra
     * @return true nếu đã tồn tại
     */
    boolean existsByUsername(String username);

    /**
     * Kiểm tra email đã tồn tại chưa.
     * 
     * @param email Email cần kiểm tra
     * @return true nếu đã tồn tại
     */
    boolean existsByEmail(String email);
}
