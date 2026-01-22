package com.ams.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database credentials
    // Note: If you use 'sa' user, change "root" to "sa"
    private static final String URL = "jdbc:mysql://localhost:3306/swp_final?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = "Abcd1234@#$"; // Mật khẩu bạn cung cấp

    // Load Driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error loading MySQL Driver: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Test connection
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối Database thành công!");
            }
        } catch (SQLException e) {
            System.err.println("Kết nối thất bại!");
            e.printStackTrace();
        }
    }
}
