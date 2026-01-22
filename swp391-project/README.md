# School Asset Management System (AMS)

## Project Overview
Hệ thống quản lý tài sản trường học (Asset Management System) hỗ trợ quản lý mua sắm, cấp phát, điều chuyển, bảo trì và thanh lý tài sản.

## Technology Stack
*   **Backend:** Java Servlet, JSP
*   **Database:** MySQL 8.0
*   **Frontend:** HTML5, CSS3, Bootstrap 5 (Recommended)
*   **Build Tool:** Maven

## Setup Instructions

### 1. Database Setup
1.  Đảm bảo Docker MySQL đang chạy.
2.  Import database:
    ```bash
    docker exec -i mysql-server mysql -u root -p < "../_bmad-output/SQL/db_final_mysql.sql"
    ```

### 2. Configure Application
*   Kiểm tra file `src/main/java/com/ams/utils/DBConnection.java`.
*   Cập nhật `USER` và `PASSWORD` nếu bạn dùng user khác `root`.

### 3. Run Application
*   Mở dự án bằng IntelliJ IDEA hoặc Eclipse.
*   Cấu hình Tomcat Server (Ver 9.0 trở lên).
*   Add Artifact `swp391-project:war exploded` vào Tomcat Deployment.
*   Run/Debug.

## Directory Structure
*   `com.ams.model`: Các class POJO (Entity).
*   `com.ams.dao`: Data Access Object (Truy vấn DB).
*   `com.ams.controller`: Servlet xử lý request.
*   `views`: Các file JSP hiển thị giao diện.
