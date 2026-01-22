-- ==========================================================
-- 0. TẠO DATABASE & THIẾT LẬP CƠ BẢN
-- ==========================================================
USE master;
GO

-- Kiểm tra và tạo database
IF EXISTS (SELECT name FROM sys.databases WHERE name = N'swp_final')
    DROP DATABASE swp_final;
GO

CREATE DATABASE swp_final;
GO

USE swp_final;
GO

-- ==========================================================
-- 1. NHÓM QUẢN TRỊ & NGƯỜI DÙNG (AUTH & USERS)
-- ==========================================================

-- Bảng Roles: Vai trò người dùng (Chốt 4 Roles theo Scope Final)
CREATE TABLE roles (
    role_id INT IDENTITY(1,1) PRIMARY KEY,
    role_name NVARCHAR(50) NOT NULL UNIQUE,
    description NVARCHAR(255)
);
GO

-- Bảng Departments: Phòng ban / Khoa (Bộ môn)
CREATE TABLE departments (
    dept_id INT IDENTITY(1,1) PRIMARY KEY,
    dept_name NVARCHAR(100) NOT NULL UNIQUE,
    description NVARCHAR(MAX)
);
GO

-- Bảng Users: Người dùng hệ thống
CREATE TABLE users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name NVARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    role_id INT NOT NULL,
    dept_id INT, -- Trưởng bộ môn thuộc bộ môn nào
    status NVARCHAR(20) DEFAULT N'Active',
    created_at DATETIME DEFAULT GETDATE(),
    
    FOREIGN KEY (role_id) REFERENCES roles(role_id),
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id) ON DELETE SET NULL,
    
    CONSTRAINT CHK_UserStatus CHECK (status IN (N'Active', N'Inactive'))
);
GO

-- ==========================================================
-- 2. NHÓM DỮ LIỆU DANH MỤC (MASTER DATA)
-- ==========================================================

-- Bảng Categories: Loại tài sản
CREATE TABLE categories (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(100) NOT NULL,
    prefix_code VARCHAR(10) NOT NULL UNIQUE, -- Dùng để sinh mã asset (VD: LAP-2024-xxx)
    description NVARCHAR(MAX)
);
GO

-- Bảng Rooms: Phòng học / Phòng chức năng
CREATE TABLE rooms (
    room_id INT IDENTITY(1,1) PRIMARY KEY,
    room_name NVARCHAR(50) NOT NULL UNIQUE,
    dept_id INT, -- Phòng thuộc quản lý của khoa nào
    capacity INT,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id) ON DELETE SET NULL
);
GO

-- Bảng Suppliers: Nhà cung cấp
CREATE TABLE suppliers (
    supplier_id INT IDENTITY(1,1) PRIMARY KEY,
    supplier_name NVARCHAR(150) NOT NULL,
    contact_person NVARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    address NVARCHAR(MAX)
);
GO

-- ==========================================================
-- 3. NHÓM QUẢN LÝ TÀI SẢN (ASSET INVENTORY)
-- ==========================================================

-- Bảng Assets: Tài sản chính
CREATE TABLE assets (
    asset_id INT IDENTITY(1,1) PRIMARY KEY,
    asset_code VARCHAR(50) NOT NULL UNIQUE, -- Mã định danh duy nhất
    asset_name NVARCHAR(150) NOT NULL,
    category_id INT NOT NULL,
    supplier_id INT,
    room_id INT, -- Vị trí hiện tại
    price DECIMAL(15, 2) DEFAULT 0,
    purchase_date DATE,
    warranty_expiry_date DATE,
    status NVARCHAR(50) DEFAULT N'New',
    description NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(),

    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id) ON DELETE SET NULL,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE SET NULL,

    -- Status theo đúng quy trình trọn đời
    CONSTRAINT CHK_AssetStatus CHECK (status IN (N'New', N'In_Use', N'Maintenance', N'Broken', N'Liquidated', N'Lost'))
);
GO

-- Bảng Asset_Images: Ảnh tài sản
CREATE TABLE asset_images (
    image_id INT IDENTITY(1,1) PRIMARY KEY,
    asset_id INT NOT NULL,
    image_url NVARCHAR(255) NOT NULL,
    uploaded_at DATETIME DEFAULT GETDATE(),
    description NVARCHAR(255),
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE
);
GO

-- Bảng Asset_History: Lịch sử vòng đời
CREATE TABLE asset_history (
    history_id INT IDENTITY(1,1) PRIMARY KEY,
    asset_id INT NOT NULL,
    action NVARCHAR(50) NOT NULL, -- Created, Transfer, Maintenance, Liquidation
    performed_by INT, 
    description NVARCHAR(MAX), 
    action_date DATETIME DEFAULT GETDATE(),

    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE,
    FOREIGN KEY (performed_by) REFERENCES users(user_id) ON DELETE SET NULL
);
GO

-- ==========================================================
-- 4. NHÓM NGHIỆP VỤ MUA SẮM (ACQUISITION & PLANNING)
-- ==========================================================

-- [NEW] Bảng Procurement_Plans: Kế hoạch mua sắm tổng hợp (Master) for FE-3
CREATE TABLE procurement_plans (
    plan_id INT IDENTITY(1,1) PRIMARY KEY,
    plan_name NVARCHAR(200) NOT NULL, -- VD: "Mua sắm Quý 1/2026"
    created_by INT NOT NULL, -- Thường là Finance Head
    created_date DATETIME DEFAULT GETDATE(),
    approved_by INT, -- Principal
    approved_date DATETIME,
    total_estimated_budget DECIMAL(18, 2) DEFAULT 0,
    status NVARCHAR(50) DEFAULT N'Draft',
    note NVARCHAR(MAX),

    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (approved_by) REFERENCES users(user_id),
    CONSTRAINT CHK_PlanStatus CHECK (status IN (N'Draft', N'Submitted', N'Approved', N'Rejected', N'Completed'))
);
GO

-- Bảng Allocation_Requests: Yêu cầu cấp phát lẻ (Detail/Child)
CREATE TABLE allocation_requests (
    request_id INT IDENTITY(1,1) PRIMARY KEY,
    created_by INT NOT NULL, -- HOD tạo
    created_date DATETIME DEFAULT GETDATE(),
    status NVARCHAR(50) DEFAULT N'Pending',
    reason_reject NVARCHAR(MAX),
    plan_id INT, -- [NEW] Link đến kế hoạch mua sắm nếu kho hết hàng
    
    FOREIGN KEY (created_by) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (plan_id) REFERENCES procurement_plans(plan_id) ON DELETE SET NULL,

    -- [UPDATE] Status flow: HOD -> Finance -> Principal (nếu cần)
    CONSTRAINT CHK_AllocStatus CHECK (status IN (
        N'Pending',              -- Chờ xử lý
        N'Approved_By_HOD',      -- HOD đã duyệt (nếu giáo viên tạo, ở đây HOD tạo nên auto pass)
        N'Approved_By_Finance',  -- Kế toán duyệt (có thể xuất kho luôn)
        N'In_Procurement_Plan',  -- Đã đưa vào kế hoạch mua sắm
        N'Rejected', 
        N'Completed'             -- Đã nhận được hàng
    ))
);
GO

CREATE TABLE allocation_details (
    detail_id INT IDENTITY(1,1) PRIMARY KEY,
    request_id INT NOT NULL,
    category_id INT NOT NULL, -- Chỉ yêu cầu loại (VD: Cần 5 Laptop), chưa có asset cụ thể
    quantity INT NOT NULL,
    note NVARCHAR(255),
    
    FOREIGN KEY (request_id) REFERENCES allocation_requests(request_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
GO

-- ==========================================================
-- 5. NHÓM NGHIỆP VỤ ĐIỀU CHUYỂN (TRANSFER)
-- ==========================================================

CREATE TABLE transfer_orders (
    transfer_id INT IDENTITY(1,1) PRIMARY KEY,
    created_by INT NOT NULL, -- Asset Staff
    source_room_id INT NOT NULL,
    dest_room_id INT NOT NULL,
    created_date DATETIME DEFAULT GETDATE(),
    approved_by INT, 
    status NVARCHAR(50) DEFAULT N'Pending',
    note NVARCHAR(MAX),

    -- [NEW] Cột theo dõi quy trình giao - nhận (FE-4)
    handover_date DATETIME, -- Ngày bên đi bàn giao
    receipt_date DATETIME,  -- Ngày bên đến nhận
    
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (source_room_id) REFERENCES rooms(room_id),
    FOREIGN KEY (dest_room_id) REFERENCES rooms(room_id),
    FOREIGN KEY (approved_by) REFERENCES users(user_id),
    
    CONSTRAINT CHK_TransferStatus CHECK (status IN (N'Pending', N'Approved', N'Handing_Over', N'Completed', N'Rejected'))
);
GO

CREATE TABLE transfer_details (
    detail_id INT IDENTITY(1,1) PRIMARY KEY,
    transfer_id INT NOT NULL,
    asset_id INT NOT NULL,
    status_at_transfer NVARCHAR(50), -- Tình trạng lúc chuyển (Tốt/Xước...)
    
    FOREIGN KEY (transfer_id) REFERENCES transfer_orders(transfer_id) ON DELETE CASCADE,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id)
);
GO

-- ==========================================================
-- 6. NHÓM BẢO TRÌ (MAINTENANCE)
-- ==========================================================

CREATE TABLE maintenance_requests (
    request_id INT IDENTITY(1,1) PRIMARY KEY,
    asset_id INT, 
    -- [REMOVED] reported_by_guest (Chỉ nội bộ)
    reported_by_user_id INT, -- HOD hoặc Staff báo
    reported_date DATETIME DEFAULT GETDATE(),
    issue_description NVARCHAR(MAX) NOT NULL,
    image_proof_url NVARCHAR(255), 
    status NVARCHAR(50) DEFAULT N'Reported',
    cost DECIMAL(15, 2) DEFAULT 0, 
    technician_note NVARCHAR(MAX),
    
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE SET NULL,
    FOREIGN KEY (reported_by_user_id) REFERENCES users(user_id) ON DELETE SET NULL,

    CONSTRAINT CHK_MaintStatus CHECK (status IN (N'Reported', N'Verified', N'In_Progress', N'Fixed', N'Cannot_Fix'))
);
GO

-- ==========================================================
-- 7. NHÓM THANH LÝ (LIQUIDATION) - [NEW MODULE]
-- ==========================================================

-- Bảng Liquidation_Minutes: Biên bản thanh lý (Master)
CREATE TABLE liquidation_minutes (
    minute_id INT IDENTITY(1,1) PRIMARY KEY,
    created_by INT NOT NULL, -- Asset Staff lập
    created_date DATETIME DEFAULT GETDATE(),
    approved_by INT, -- Principal duyệt
    approved_date DATETIME,
    status NVARCHAR(50) DEFAULT N'Pending',
    note NVARCHAR(MAX), -- Lý do thanh lý chung (VD: Hết niên hạn sử dụng lô PC 2018)

    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (approved_by) REFERENCES users(user_id),
    CONSTRAINT CHK_LiquidStatus CHECK (status IN (N'Pending', N'Approved', N'Rejected', N'Completed'))
);
GO

-- Chi tiết các tài sản trong đợt thanh lý
CREATE TABLE liquidation_details (
    detail_id INT IDENTITY(1,1) PRIMARY KEY,
    minute_id INT NOT NULL,
    asset_id INT NOT NULL,
    reason NVARCHAR(MAX), -- Lý do cụ thể cho từng món (Hỏng main, vỡ màn hình...)
    salvage_value DECIMAL(15, 2) DEFAULT 0, -- Giá trị thu hồi (nếu bán ve chai)

    FOREIGN KEY (minute_id) REFERENCES liquidation_minutes(minute_id) ON DELETE CASCADE,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id)
);
GO

-- ==========================================================
-- 8. DỮ LIỆU MẪU (SEED DATA - STANDARDIZED)
-- ==========================================================

-- Insert Roles (Chỉ 4 Role chuẩn theo tài liệu)
INSERT INTO roles (role_name, description) VALUES 
(N'Principal', N'Hiệu trưởng - Duyệt tối cao, xem báo cáo'),
(N'Finance_Head', N'Trưởng phòng TC-KT - Quản lý tài sản, ngân sách'),
(N'Asset_Staff', N'Nhân viên thiết bị - Tác nghiệp nhập liệu, bảo trì'),
(N'Head_of_Dept', N'Trưởng bộ môn - Yêu cầu cấp phát, quản lý phòng');
GO

-- Insert Departments
INSERT INTO departments (dept_name) VALUES 
(N'Ban Giám Hiệu'), 
(N'Phòng Hành chính - Kế toán'), 
(N'Tổ Kỹ thuật - Tài sản'),
(N'Bộ môn Tin học'),
(N'Bộ môn Vật lý');
GO

-- Insert Categories
INSERT INTO categories (category_name, prefix_code) VALUES 
(N'Laptop & Máy tính', 'COM'), 
(N'Máy chiếu & Tivi', 'PJT'), 
(N'Bàn ghế học sinh', 'FUR'),
(N'Thiết bị thí nghiệm', 'LAB');
GO

-- Seed Admin User (Finance Head)
INSERT INTO users (username, password_hash, full_name, email, role_id, dept_id) VALUES 
('admin', 'HASH_PASS_123', N'Nguyễn Văn Kế Toán', 'finance@school.edu.vn', 2, 2);
GO
