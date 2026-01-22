-- ==========================================================
-- 0. TẠO DATABASE & THIẾT LẬP CƠ BẢN
-- ==========================================================
DROP DATABASE IF EXISTS swp_final;
CREATE DATABASE swp_final CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE swp_final;

-- ==========================================================
-- 1. NHÓM QUẢN TRỊ & NGƯỜI DÙNG (AUTH & USERS)
-- ==========================================================

-- Bảng Roles: Vai trò người dùng
CREATE TABLE roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- Bảng Departments: Phòng ban / Khoa (Bộ môn)
CREATE TABLE departments (
    dept_id INT AUTO_INCREMENT PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

-- Bảng Users: Người dùng hệ thống
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    role_id INT NOT NULL,
    dept_id INT, -- Trưởng bộ môn thuộc bộ môn nào
    status ENUM('Active', 'Inactive') DEFAULT 'Active',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (role_id) REFERENCES roles(role_id),
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id) ON DELETE SET NULL
);

-- ==========================================================
-- 2. NHÓM DỮ LIỆU DANH MỤC (MASTER DATA)
-- ==========================================================

-- Bảng Categories: Loại tài sản
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    prefix_code VARCHAR(10) NOT NULL UNIQUE, -- Dùng để sinh mã asset (VD: LAP-2024-xxx)
    description TEXT
);

-- Bảng Rooms: Phòng học / Phòng chức năng
CREATE TABLE rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_name VARCHAR(50) NOT NULL UNIQUE,
    dept_id INT, -- Phòng thuộc quản lý của khoa nào
    capacity INT,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id) ON DELETE SET NULL
);

-- Bảng Suppliers: Nhà cung cấp
CREATE TABLE suppliers (
    supplier_id INT AUTO_INCREMENT PRIMARY KEY,
    supplier_name VARCHAR(150) NOT NULL,
    contact_person VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    address TEXT
);

-- ==========================================================
-- 3. NHÓM QUẢN LÝ TÀI SẢN (ASSET INVENTORY)
-- ==========================================================

-- Bảng Assets: Tài sản chính
CREATE TABLE assets (
    asset_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_code VARCHAR(50) NOT NULL UNIQUE, -- Mã định danh duy nhất
    asset_name VARCHAR(150) NOT NULL,
    category_id INT NOT NULL,
    supplier_id INT,
    room_id INT, -- Vị trí hiện tại
    price DECIMAL(15, 2) DEFAULT 0,
    purchase_date DATE,
    warranty_expiry_date DATE,
    -- Status theo đúng quy trình trọn đời
    status ENUM('New', 'In_Use', 'Maintenance', 'Broken', 'Liquidated', 'Lost') DEFAULT 'New',
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id) ON DELETE SET NULL,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE SET NULL
);

-- Bảng Asset_Images: Ảnh tài sản
CREATE TABLE asset_images (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id INT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    uploaded_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    description VARCHAR(255),
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE
);

-- Bảng Asset_History: Lịch sử vòng đời
CREATE TABLE asset_history (
    history_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id INT NOT NULL,
    action VARCHAR(50) NOT NULL, -- Created, Transfer, Maintenance, Liquidation
    performed_by INT, 
    description TEXT, 
    action_date DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE,
    FOREIGN KEY (performed_by) REFERENCES users(user_id) ON DELETE SET NULL
);

-- ==========================================================
-- 4. NHÓM NGHIỆP VỤ MUA SẮM (ACQUISITION & PLANNING)
-- ==========================================================

-- Bảng Procurement_Plans: Kế hoạch mua sắm tổng hợp (Master)
CREATE TABLE procurement_plans (
    plan_id INT AUTO_INCREMENT PRIMARY KEY,
    plan_name VARCHAR(200) NOT NULL, -- VD: "Mua sắm Quý 1/2026"
    created_by INT NOT NULL, -- Thường là Finance Head
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    approved_by INT, -- Principal
    approved_date DATETIME,
    total_estimated_budget DECIMAL(18, 2) DEFAULT 0,
    status ENUM('Draft', 'Submitted', 'Approved', 'Rejected', 'Completed') DEFAULT 'Draft',
    note TEXT,

    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (approved_by) REFERENCES users(user_id)
);

-- Bảng Allocation_Requests: Yêu cầu cấp phát lẻ (Detail/Child)
CREATE TABLE allocation_requests (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    created_by INT NOT NULL, -- HOD tạo
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    -- Status flow: HOD -> Finance -> Principal (nếu cần)
    status ENUM('Pending', 'Approved_By_HOD', 'Approved_By_Finance', 'In_Procurement_Plan', 'Rejected', 'Completed') DEFAULT 'Pending',
    reason_reject TEXT,
    plan_id INT, -- Link đến kế hoạch mua sắm nếu kho hết hàng
    
    FOREIGN KEY (created_by) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (plan_id) REFERENCES procurement_plans(plan_id) ON DELETE SET NULL
);

CREATE TABLE allocation_details (
    detail_id INT AUTO_INCREMENT PRIMARY KEY,
    request_id INT NOT NULL,
    category_id INT NOT NULL, -- Chỉ yêu cầu loại (VD: Cần 5 Laptop), chưa có asset cụ thể
    quantity INT NOT NULL,
    note VARCHAR(255),
    
    FOREIGN KEY (request_id) REFERENCES allocation_requests(request_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- ==========================================================
-- 5. NHÓM NGHIỆP VỤ ĐIỀU CHUYỂN (TRANSFER)
-- ==========================================================

CREATE TABLE transfer_orders (
    transfer_id INT AUTO_INCREMENT PRIMARY KEY,
    created_by INT NOT NULL, -- Asset Staff
    source_room_id INT NOT NULL,
    dest_room_id INT NOT NULL,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    approved_by INT, 
    status ENUM('Pending', 'Approved', 'Handing_Over', 'Completed', 'Rejected') DEFAULT 'Pending',
    note TEXT,

    -- Cột theo dõi quy trình giao - nhận (FE-4)
    handover_date DATETIME, -- Ngày bên đi bàn giao
    receipt_date DATETIME,  -- Ngày bên đến nhận
    
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (source_room_id) REFERENCES rooms(room_id),
    FOREIGN KEY (dest_room_id) REFERENCES rooms(room_id),
    FOREIGN KEY (approved_by) REFERENCES users(user_id)
);

CREATE TABLE transfer_details (
    detail_id INT AUTO_INCREMENT PRIMARY KEY,
    transfer_id INT NOT NULL,
    asset_id INT NOT NULL,
    status_at_transfer VARCHAR(50), -- Tình trạng lúc chuyển (Tốt/Xước...)
    
    FOREIGN KEY (transfer_id) REFERENCES transfer_orders(transfer_id) ON DELETE CASCADE,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id)
);

-- ==========================================================
-- 6. NHÓM BẢO TRÌ (MAINTENANCE)
-- ==========================================================

CREATE TABLE maintenance_requests (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id INT, 
    reported_by_user_id INT, -- HOD hoặc Staff báo
    reported_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    issue_description TEXT NOT NULL,
    image_proof_url VARCHAR(255), 
    status ENUM('Reported', 'Verified', 'In_Progress', 'Fixed', 'Cannot_Fix') DEFAULT 'Reported',
    cost DECIMAL(15, 2) DEFAULT 0, 
    technician_note TEXT,
    
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE SET NULL,
    FOREIGN KEY (reported_by_user_id) REFERENCES users(user_id) ON DELETE SET NULL
);

-- ==========================================================
-- 7. NHÓM THANH LÝ (LIQUIDATION) - [NEW MODULE]
-- ==========================================================

-- Bảng Liquidation_Minutes: Biên bản thanh lý (Master)
CREATE TABLE liquidation_minutes (
    minute_id INT AUTO_INCREMENT PRIMARY KEY,
    created_by INT NOT NULL, -- Asset Staff lập
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    approved_by INT, -- Principal duyệt
    approved_date DATETIME,
    status ENUM('Pending', 'Approved', 'Rejected', 'Completed') DEFAULT 'Pending',
    note TEXT, -- Lý do thanh lý chung (VD: Hết niên hạn sử dụng lô PC 2018)

    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (approved_by) REFERENCES users(user_id)
);

-- Chi tiết các tài sản trong đợt thanh lý
CREATE TABLE liquidation_details (
    detail_id INT AUTO_INCREMENT PRIMARY KEY,
    minute_id INT NOT NULL,
    asset_id INT NOT NULL,
    reason TEXT, -- Lý do cụ thể cho từng món (Hỏng main, vỡ màn hình...)
    salvage_value DECIMAL(15, 2) DEFAULT 0, -- Giá trị thu hồi (nếu bán ve chai)

    FOREIGN KEY (minute_id) REFERENCES liquidation_minutes(minute_id) ON DELETE CASCADE,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id)
);

-- ==========================================================
-- 8. DỮ LIỆU MẪU (SEED DATA - STANDARDIZED)
-- ==========================================================

-- Insert Roles (Chỉ 4 Role chuẩn theo tài liệu)
INSERT INTO roles (role_name, description) VALUES 
('Principal', 'Hiệu trưởng - Duyệt tối cao, xem báo cáo'),
('Finance_Head', 'Trưởng phòng TC-KT - Quản lý tài sản, ngân sách'),
('Asset_Staff', 'Nhân viên thiết bị - Tác nghiệp nhập liệu, bảo trì'),
('Head_of_Dept', 'Trưởng bộ môn - Yêu cầu cấp phát, quản lý phòng');

-- Insert Departments
INSERT INTO departments (dept_name) VALUES 
('Ban Giám Hiệu'), 
('Phòng Hành chính - Kế toán'), 
('Tổ Kỹ thuật - Tài sản'),
('Bộ môn Tin học'),
('Bộ môn Vật lý');

-- Insert Categories
INSERT INTO categories (category_name, prefix_code) VALUES 
('Laptop & Máy tính', 'COM'), 
('Máy chiếu & Tivi', 'PJT'), 
('Bàn ghế học sinh', 'FUR'),
('Thiết bị thí nghiệm', 'LAB');

-- Seed Admin User (Finance Head)
-- Lưu ý: ID của Roles/Dept phụ thuộc vào thứ tự insert, trong MySQL auto_increment bắt đầu từ 1.
-- Principal=1, Finance_Head=2, Asset_Staff=3, Head_of_Dept=4
-- Ban Giám Hiệu=1, Phòng Hành chính - Kế toán=2
INSERT INTO users (username, password_hash, full_name, email, role_id, dept_id) VALUES 
('admin', 'HASH_PASS_123', 'Nguyễn Văn Kế Toán', 'finance@school.edu.vn', 2, 2);
