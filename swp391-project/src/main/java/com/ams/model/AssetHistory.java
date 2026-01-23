package com.ams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetHistory {
    private Integer historyId;
    private Integer assetId;
    private String action; // Created, Transfer, Maintenance, Liquidation
    private Integer performedBy; // User ID
    private String description;
    private LocalDateTime actionDate;

    // Joined data for display
    private User performer;

    // Helper for JSTL fmt tag
    public java.util.Date getActionDateAsDate() {
        if (actionDate == null)
            return null;
        return java.util.Date.from(actionDate.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }
}
