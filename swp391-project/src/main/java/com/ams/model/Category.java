package com.ams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Integer categoryId;
    private String categoryName;
    private String prefixCode;
    private String description;

    // Constructor for creating new category
    public Category(String categoryName, String prefixCode, String description) {
        this.categoryName = categoryName;
        this.prefixCode = prefixCode;
        this.description = description;
    }
}
