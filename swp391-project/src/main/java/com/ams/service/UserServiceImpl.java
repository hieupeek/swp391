package com.ams.service;

import com.ams.dao.UserDAO;
import com.ams.dao.UserDAOImpl;
import com.ams.model.User;
import com.ams.model.UserStatus;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

/**
 * Triển khai UserService.
 * Xử lý nghiệp vụ liên quan đến quản lý và xác thực người dùng.
 * Sử dụng BCrypt cho password hashing.
 */
public class UserServiceImpl implements UserService {

    /**
     * Số rounds cho BCrypt hashing.
     * Giá trị 12 cân bằng giữa bảo mật và performance.
     */
    private static final int BCRYPT_ROUNDS = 12;

    private final UserDAO userDAO;

    /**
     * Constructor mặc định - sử dụng UserDAOImpl.
     */
    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    /**
     * Constructor với dependency injection.
     * 
     * @param userDAO DAO instance để inject
     */
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> authenticate(String username, String password) {
        // Validate input
        if (username == null || username.isBlank() || password == null || password.isEmpty()) {
            return Optional.empty();
        }

        // Tìm user theo username
        Optional<User> userOpt = userDAO.findByUsername(username.trim());

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        // Kiểm tra user có active không
        if (user.getStatus() != UserStatus.Active) {
            return Optional.empty();
        }

        // Verify password với BCrypt
        if (BCrypt.checkpw(password, user.getPasswordHash())) {
            // Lấy user với đầy đủ thông tin Role/Department
            return userDAO.findByIdWithDetails(user.getUserId());
        }

        return Optional.empty();
    }

    @Override
    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        // Validate input
        if (userId == null || oldPassword == null || newPassword == null) {
            return false;
        }

        if (newPassword.length() < 6) {
            return false; // Password phải ít nhất 6 ký tự
        }

        // Tìm user
        Optional<User> userOpt = userDAO.findById(userId);
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();

        // Verify old password
        if (!BCrypt.checkpw(oldPassword, user.getPasswordHash())) {
            return false;
        }

        // Hash new password và update
        String newPasswordHash = hashPassword(newPassword);
        return userDAO.updatePassword(userId, newPasswordHash);
    }

    @Override
    public Optional<User> getUserById(Integer userId) {
        if (userId == null || userId <= 0) {
            return Optional.empty();
        }
        return userDAO.findByIdWithDetails(userId);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        if (username == null || username.isBlank()) {
            return Optional.empty();
        }
        return userDAO.findByUsername(username.trim());
    }

    @Override
    public User createUser(User user, String password) {
        // Validate input
        if (user == null || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("User và password không được null");
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username không được để trống");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email không được để trống");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("Password phải ít nhất 6 ký tự");
        }

        // Kiểm tra username/email đã tồn tại chưa
        if (userDAO.existsByUsername(user.getUsername().trim())) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }

        if (userDAO.existsByEmail(user.getEmail().trim())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        // Hash password
        String passwordHash = hashPassword(password);
        user.setPasswordHash(passwordHash);
        user.setUsername(user.getUsername().trim());
        user.setEmail(user.getEmail().trim());

        // Set default status nếu chưa có
        if (user.getStatus() == null) {
            user.setStatus(UserStatus.Active);
        }

        return userDAO.save(user);
    }

    @Override
    public boolean updateProfile(User user) {
        if (user == null || user.getUserId() == null) {
            return false;
        }

        // Kiểm tra user tồn tại
        Optional<User> existingUser = userDAO.findById(user.getUserId());
        if (existingUser.isEmpty()) {
            return false;
        }

        // Kiểm tra email mới có bị trùng với user khác không
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            Optional<User> userWithEmail = userDAO.findByEmail(user.getEmail().trim());
            if (userWithEmail.isPresent() && !userWithEmail.get().getUserId().equals(user.getUserId())) {
                return false; // Email đã được sử dụng bởi user khác
            }
        }

        return userDAO.update(user);
    }

    @Override
    public boolean isUsernameExists(String username) {
        if (username == null || username.isBlank()) {
            return false;
        }
        return userDAO.existsByUsername(username.trim());
    }

    @Override
    public boolean isEmailExists(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return userDAO.existsByEmail(email.trim());
    }

    // ========== Helper Methods ==========

    /**
     * Hash password bằng BCrypt.
     * 
     * @param plainPassword Password plain text
     * @return Password đã được hash
     */
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_ROUNDS));
    }
}
