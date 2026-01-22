package com.ams.model;

/**
 * Enum đại diện cho trạng thái của người dùng trong hệ thống.
 * Sử dụng trong bảng users, cột status.
 */
public enum UserStatus {
    /**
     * Người dùng đang hoạt động, có thể đăng nhập và sử dụng hệ thống.
     */
    Active,

    /**
     * Người dùng đã bị vô hiệu hóa, không thể đăng nhập.
     */
    Inactive
}
