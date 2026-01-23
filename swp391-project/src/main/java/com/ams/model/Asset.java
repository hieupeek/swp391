package com.ams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asset {
    private Integer assetId;
    private String assetCode;
    private String assetName;
    private Integer categoryId;
    private Integer supplierId;
    private Integer roomId;
    private BigDecimal price;
    private LocalDate purchaseDate;
    private LocalDate warrantyExpiryDate;
    private String status;
    private String description;

    // Display fields (Joined)
    private Category category;
    private Supplier supplier;
    private Room room;
}
