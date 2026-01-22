package com.ams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class đại diện cho phòng ban/khoa trong hệ thống.
 * Tương ứng với bảng 'departments' trong database.
 * 
 * Các phòng ban mẫu:
 * - Ban Giám Hiệu
 * - Phòng Hành chính - Kế toán
 * - Tổ Kỹ thuật - Tài sản
 * - Bộ môn Tin học
 * - Bộ môn Vật lý
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    /**
     * ID của phòng ban (Primary Key, Auto Increment).
     */
    private Integer deptId;

    /**
     * Tên phòng ban (Unique, Not Null).
     */
    private String deptName;

    /**
     * Mô tả chi tiết về phòng ban.
     */
    private String description;

    /**
     * Constructor tạo Department mới (không có ID - dùng khi insert).
     * 
     * @param deptName    Tên phòng ban
     * @param description Mô tả phòng ban
     */
    public Department(String deptName, String description) {
        this.deptName = deptName;
        this.description = description;
    }
}
