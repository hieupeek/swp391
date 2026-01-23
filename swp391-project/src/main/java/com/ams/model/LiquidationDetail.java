package com.ams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Model đại diện cho chi tiết tài sản trong biên bản thanh lý.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiquidationDetail {
    private Integer detailId;
    private Integer minuteId;
    private Integer assetId;
    private String reason; // Lý do thanh lý cụ thể
    private BigDecimal salvageValue; // Giá trị thu hồi

    // Joined data for display
    private Asset asset;
}
