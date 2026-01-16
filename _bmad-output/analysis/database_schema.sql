-- Database Schema for School Asset Management System (AMS)
-- Version: 1.0
-- Database System: MySQL 8.0+

DROP DATABASE IF EXISTS school_asset_db;
CREATE DATABASE school_asset_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE school_asset_db;

-- =============================================
-- GROUP 1: MASTER DATA
-- =============================================

-- 1. Departments (Phòng ban/Bộ môn)
CREATE TABLE departments (
    dept_id INT AUTO_INCREMENT PRIMARY KEY,
    dept_name NVARCHAR(100) NOT NULL,
    dept_type ENUM('ACADEMIC', 'ADMIN') NOT NULL DEFAULT 'ACADEMIC',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Rooms (Phòng học/Phòng chức năng)
CREATE TABLE rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_name NVARCHAR(100) NOT NULL,
    capacity INT DEFAULT 30,
    dept_id INT, -- Optional: Phòng thuộc quản lý của bộ môn nào
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id) ON DELETE SET NULL
);

-- 3. Categories (Danh mục tài sản)
CREATE TABLE categories (
    cate_id INT AUTO_INCREMENT PRIMARY KEY,
    cate_name NVARCHAR(100) NOT NULL,
    prefix_code VARCHAR(10) NOT NULL UNIQUE, -- e.g. 'LAP', 'PC'
    life_span INT DEFAULT 5, -- Useful life in years for depreciation
    active BOOLEAN DEFAULT TRUE
);

-- 4. Users (Người dùng)
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Stored as BCrypt hash
    full_name NVARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    role ENUM('PRINCIPAL', 'FINANCE', 'STAFF', 'HOD', 'TEACHER') NOT NULL,
    dept_id INT,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id) ON DELETE SET NULL
);

-- =============================================
-- GROUP 2: CORE ASSET DATA
-- =============================================

-- 5. Assets (Tài sản)
CREATE TABLE assets (
    asset_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_code VARCHAR(20) NOT NULL UNIQUE, -- e.g. 'LAP-2024-001'
    asset_name NVARCHAR(200) NOT NULL,
    cate_id INT NOT NULL,
    purchase_date DATE,
    price DECIMAL(15, 2) DEFAULT 0.00,
    warranty_expiry DATE,
    current_status ENUM('NEW', 'IN_USE', 'BROKEN', 'UNDER_MAINTENANCE', 'LIQUIDATED', 'LOST') DEFAULT 'NEW',
    current_room_id INT,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cate_id) REFERENCES categories(cate_id),
    FOREIGN KEY (current_room_id) REFERENCES rooms(room_id) ON DELETE SET NULL
);

-- 6. Asset History (Lịch sử/Audit Log)
CREATE TABLE asset_history (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id INT NOT NULL,
    action VARCHAR(50) NOT NULL, -- 'CREATE', 'TRANSFER', 'MAINTENANCE'
    performed_by INT,
    performed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE,
    FOREIGN KEY (performed_by) REFERENCES users(user_id)
);

-- =============================================
-- GROUP 3: TRANSACTIONAL DATA
-- =============================================

-- 7. Requests (Yêu cầu Mua sắm/Cấp phát)
CREATE TABLE requests (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    requester_id INT NOT NULL,
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    urgency ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'LOW',
    status ENUM('PENDING', 'APPROVED_HOD', 'APPROVED_PRINCIPAL', 'REJECTED', 'COMPLETED') DEFAULT 'PENDING',
    approver_note TEXT,
    FOREIGN KEY (requester_id) REFERENCES users(user_id)
);

-- 8. Request Items (Chi tiết yêu cầu)
CREATE TABLE request_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    request_id INT NOT NULL,
    item_name NVARCHAR(200) NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    reason TEXT,
    FOREIGN KEY (request_id) REFERENCES requests(request_id) ON DELETE CASCADE
);

-- 9. Transfer Tickets (Phiếu điều chuyển)
CREATE TABLE transfer_tickets (
    transfer_id INT AUTO_INCREMENT PRIMARY KEY,
    created_by INT NOT NULL, -- Asset Staff
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    src_room_id INT NOT NULL,
    dest_room_id INT NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'HANDOVER_CONFIRMED', 'COMPLETED', 'REJECTED') DEFAULT 'PENDING',
    manager_approval_date TIMESTAMP NULL,
    note TEXT,
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (src_room_id) REFERENCES rooms(room_id),
    FOREIGN KEY (dest_room_id) REFERENCES rooms(room_id)
);

-- 10. Transfer Details (Chi tiết điều chuyển - Nhiều tài sản 1 phiếu)
CREATE TABLE transfer_details (
    detail_id INT AUTO_INCREMENT PRIMARY KEY,
    transfer_id INT NOT NULL,
    asset_id INT NOT NULL,
    note TEXT, -- Ghi chú tình trạng lúc chuyển
    FOREIGN KEY (transfer_id) REFERENCES transfer_tickets(transfer_id) ON DELETE CASCADE,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id)
);

-- 11. Maintenance Tickets (Phiếu bảo trì)
CREATE TABLE maintenance_tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id INT NOT NULL,
    reported_by INT,
    reported_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    issue_description TEXT,
    cost DECIMAL(15, 2) DEFAULT 0.00,
    status ENUM('REPORTED', 'IN_PROGRESS', 'COMPLETED', 'CANNOT_FIX') DEFAULT 'REPORTED',
    technician_note TEXT,
    completed_date TIMESTAMP NULL,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id),
    FOREIGN KEY (reported_by) REFERENCES users(user_id)
);

-- 12. Liquidation Minutes (Biên bản thanh lý)
CREATE TABLE liquidation_minutes (
    liquidation_id INT AUTO_INCREMENT PRIMARY KEY,
    created_by INT NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDING', 'APPROVED', 'FINALIZED') DEFAULT 'PENDING',
    total_amount DECIMAL(15, 2) DEFAULT 0.00, -- Tổng tiền thanh lý thu về
    notes TEXT,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- 13. Liquidation Details (Chi tiết thanh lý)
CREATE TABLE liquidation_details (
    detail_id INT AUTO_INCREMENT PRIMARY KEY,
    liquidation_id INT NOT NULL,
    asset_id INT NOT NULL,
    reason TEXT, -- 'Hỏng không sửa được', 'Hết khấu hao'
    resale_price DECIMAL(15, 2) DEFAULT 0.00,
    FOREIGN KEY (liquidation_id) REFERENCES liquidation_minutes(liquidation_id) ON DELETE CASCADE,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id)
);

-- Create Indexes for performance
CREATE INDEX idx_asset_code ON assets(asset_code);
CREATE INDEX idx_asset_status ON assets(current_status);
CREATE INDEX idx_request_status ON requests(status);
CREATE INDEX idx_transfer_status ON transfer_tickets(status);
