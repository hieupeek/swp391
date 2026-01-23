package com.ams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    private Integer supplierId;
    private String supplierName;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
}
