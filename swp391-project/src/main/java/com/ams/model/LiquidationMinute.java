package com.ams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Model đại diện cho Biên bản Thanh lý (Master).
 * Mỗi biên bản có thể chứa nhiều tài sản cần thanh lý.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiquidationMinute {
    private Integer minuteId;
    private Integer createdBy;
    private LocalDateTime createdDate;
    private Integer approvedBy;
    private LocalDateTime approvedDate;
    private String status; // Pending, Approved, Rejected, Completed
    private String note;

    // Joined data for display
    private User creator;
    private User approver;

    // Helper for JSTL
    public java.util.Date getCreatedDateAsDate() {
        if (createdDate == null)
            return null;
        return java.util.Date.from(createdDate.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }

    public java.util.Date getApprovedDateAsDate() {
        if (approvedDate == null)
            return null;
        return java.util.Date.from(approvedDate.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }
}
