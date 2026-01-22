package com.ams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private Integer roomId;
    private String roomName;
    private Integer deptId;
    private Integer capacity;

    // Additional field for display if needed (e.g., Department Name)
    private Department department;
}
