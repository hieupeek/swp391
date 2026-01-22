package com.ams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class đại diện cho vai trò người dùng trong hệ thống.
 * Tương ứng với bảng 'roles' trong database.
 * 
 * Các vai trò chuẩn:
 * - Principal (Hiệu trưởng)
 * - Finance_Head (Trưởng phòng TC-KT)
 * - Asset_Staff (Nhân viên thiết bị)
 * - Head_of_Dept (Trưởng bộ môn)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    /**
     * ID của vai trò (Primary Key, Auto Increment).
     */
    private Integer roleId;

    /**
     * Tên vai trò (Unique, Not Null).
     * VD: "Principal", "Finance_Head", "Asset_Staff", "Head_of_Dept"
     */
    private String roleName;

    /**
     * Mô tả chi tiết về vai trò.
     */
    private String description;

    /**
     * Constructor tạo Role mới (không có ID - dùng khi insert).
     * 
     * @param roleName    Tên vai trò
     * @param description Mô tả vai trò
     */
    public Role(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }
}
