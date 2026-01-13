---
selected_approach: 'progressive-flow'
techniques_used: ['What If Scenarios', 'Context Analysis', 'Mind Mapping', 'SCAMPER Method', 'Solution Matrix']
stepsCompleted: [1, 2]
---

# Software Requirements Specification (SRS) - Group 6

**Topic:** Asset Management System (AMS)
**Context:** Public High School
**Date:** 2026-01-12

---

## I. Overview / Tổng quan

### 1. System Context / Bối cảnh hệ thống
**English:**
The Asset Management System (AMS) is a web-based platform designed for public high schools. It streamlines the lifecycle of assets—from the initial request to the Ministry of Education, through intake, allocation, maintenance, and final return or liquidation. It replaces manual, paper-based tracking with a digital ledger to ensure accountability and efficiency.

**Tiếng Việt:**
Hệ thống Quản lý Tài sản (AMS) là một nền tảng web dành cho các trường THPT công lập. Hệ thống tối ưu hóa toàn bộ vòng đời của tài sản—từ khâu đề xuất lên Bộ Giáo dục, tiếp nhận, cấp phát, bảo trì, cho đến khi hoàn trả hoặc thanh lý. AMS thay thế việc quản lý thủ công bằng giấy tờ bằng sổ cái kỹ thuật số, giúp tăng cường tính minh bạch và hiệu quả quản lý.

### 2. External Entities / Các đối tượng liên quan
| # | Entity | Mô tả (Description) |
|---|--------|-------------|
| 1 | **Guest** (Khách) | **EN:** Anonymous users (Students/Visitors/Teachers) who report damaged property via QR codes. <br> **VN:** Người dùng ẩn danh (Học sinh/Khách/Giáo viên) báo cáo hỏng hóc qua mã QR. |
| 2 | **Facilities Staff** (NV Thiết bị) | **EN:** Operational users who manage inventory, verify reports, and handle physical asset movements. <br> **VN:** Người vận hành chính: quản lý kho, xác minh báo cáo và điều chuyển tài sản. |
| 3 | **Vice Principal** (Phó Hiệu trưởng) | **EN:** Secondary approver and internal overseer of facility status and maintenance requests. <br> **VN:** Người kiểm duyệt cấp 1, giám sát tình trạng cơ sở vật chất và các yêu cầu bảo trì. |
| 4 | **Principal** (Hiệu trưởng) | **EN:** High-level approver for provisioning requests and consumer of executive reports. <br> **VN:** Người phê duyệt cao nhất cho các đề xuất mua sắm và theo dõi báo cáo tổng hợp. |

### 3. Business Processes / Quy trình nghiệp vụ
The system follows the "Provisioning & Handover" model / Hệ thống tuân theo mô hình "Cung ứng & Bàn giao":
- **Provisioning Flow (Quy trình Cung ứng):** Staff summarizes needs -> Vice Principal reviews -> Principal approves -> Export PDF to Ministry. (Nhân viên tổng hợp nhu cầu -> Phó Hiệu trưởng soát xét -> Hiệu trưởng phê duyệt -> Xuất PDF gửi Bộ).
- **Intake Flow (Quy trình Tiếp nhận):** Ministry delivers items -> Staff performs physical verify -> Input data into System. (Bộ bàn giao tài sản -> Nhân viên kiểm kê thực tế -> Nhập dữ liệu vào hệ thống).
- **Maintenance Flow (Quy trình Bảo trì):** Guest/Staff reports damage -> Staff verifies -> Vice Principal approves repair -> Staff records result. (Khách/NV báo hỏng -> NV xác minh -> Phó Hiệu trưởng duyệt sửa chữa -> NV cập nhật kết quả).
- **Liquidation/Return Flow (Quy trình Thanh lý/Thu hồi):** Staff identifies unrepairable/obsolete assets -> Principal approves -> Generate return document -> Physical return to Ministry -> Update inventory status. (Nhân viên xác định tài sản hỏng không thể sửa chữa/hết hạn -> Hiệu trưởng phê duyệt -> Tạo biên bản bàn giao -> Hoàn trả thực tế về Bộ -> Cập nhật trạng thái trên hệ thống).

---

## 4. User Requirements / Yêu cầu người dùng
This section identifies all Use Cases (UC) for the system actors. / Phần này xác định các ca sử dụng (UC) cho các đối tượng trong hệ thống.

| ID | Use Case Name | Feature Module | Primary Actor | Description (Mô tả) |
| :--- | :--- | :--- | :--- | :--- |
| **UC01** | Report Damage | Maintenance | Guest | **EN:** Report asset issues via QR code (anonymous, photo attachment). <br> **VN:** Báo cáo hỏng hóc qua mã QR (ẩn danh, kèm ảnh). |
| **UC02** | View Inventory | Asset Management | Facilities Staff | **EN:** Browse, filter, and search the school's asset registry. <br> **VN:** Xem, lọc và tìm kiếm danh mục tài sản của trường. |
| **UC03** | Add Inventory | Inventory Addition | Facilities Staff | **EN:** Record new assets received from Ministry documentation. <br> **VN:** Ghi nhận tài sản mới từ danh mục của Bộ bàn giao. |
| **UC04** | Allocate Asset | Asset Allocation | Facilities Staff | **EN:** Assign assets to specific rooms, departments, or personnel. <br> **VN:** Cấp phát tài sản cho phòng học hoặc cá nhân cụ thể. |
| **UC05** | Create Provisioning | Provisioning | Facilities Staff | **EN:** Compile asset needs and generate formal request documents. <br> **VN:** Tổng hợp nhu cầu và tạo văn bản đề xuất mua sắm. |
| **UC06** | Review Request | Approval Workflow | Vice Principal | **EN:** Review, revise, or provide initial approval for requests. <br> **VN:** Xem xét, chỉnh sửa hoặc duyệt sơ bộ các yêu cầu. |
| **UC07** | Final Approval | Approval Workflow | Principal | **EN:** Final validation and formal sign-off on asset requests. <br> **VN:** Kiểm tra cuối cùng và phê duyệt chính thức yêu cầu tài sản. |
| **UC08** | View Dashboard | Reports & Stats | Principal | **EN:** Access visual analytics and summary reports of school assets. <br> **VN:** Xem biểu đồ phân tích và báo cáo tổng hợp cơ sở vật chất. |
| **UC09** | Manage Master Data | System Admin | Facilities Staff | **EN:** Configure system settings (Room types, Categories, Statuses). <br> **VN:** Cấu hình cài đặt hệ thống (Loại phòng, Danh mục, Trạng thái). |

### 4.1 UCs for Guest / Ca sử dụng cho Khách
- **UC01: Report Damage:** Allows any person on site to scan a QR code on an object/room to report a fault. (Cho phép bất kỳ ai tại trường quét mã QR để báo cáo hỏng hóc vật dụng/phòng học).

### 4.2 UCs for Facilities Staff / Ca sử dụng cho NV Thiết bị
- **UC02-UC05, UC09:** Full operational control over assets, allocations, and system master data. (Toàn quyền vận hành tài sản, cấp phát và quản lý dữ liệu hệ thống).

### 4.3 UCs for Vice Principal & Principal / Ca sử dụng cho BGH
- **UC06-UC08:** High-level oversight, multi-stage approval workflow, and strategic reporting. (Giám sát cấp cao, quy trình phê duyệt đa cấp và báo cáo chiến lược).

---

## 5. System Functionalities

### 5.1 Screens Flow
- **Public:** Landing Page (QR Form) -> Success Message.
- **Internal:** Login -> Dashboard -> [Module Lists] -> [Detail Views] -> [Approval Workflows].

### 5.2 Screen Authorization
| Screen Name | Guest | Facilities Staff | Vice Principal | Principal |
|---|:---:|:---:|:---:|:---:|
| Anonymous Report Form | X | | | |
| Dashboard Overview | | X (View) | X (View) | X (Full) |
| Asset Inventory List | | X (Full) | X (View) | X (View) |
| Provisioning Workflow | | X (Create) | X (Review) | X (Approve) |
| Master Data Settings | | X (Full) | | |
| User Management | | X (Admin) | | |

### 5.3 Non-UI Functions
| # | Feature | System Function | Description (Mô tả) |
|---|---|---|---|
| 1 | Provisioning | PDF Generator | **EN:** Generates the official Ministry request form as a PDF document. <br> **VN:** Tự động tạo mẫu đơn đề xuất gửi Bộ dưới dạng tệp PDF. |
| 2 | QR Management | QR Seed Generator | **EN:** System generates unique URLs for each asset/room to be printed as QR codes. <br> **VN:** Hệ thống tạo các liên kết duy nhất cho mỗi tài sản/phòng để in mã QR. |
| 3 | Notifications | Activity Logger | **EN:** Tracks all approval steps (Who, When, Action) for audit purposes. <br> **VN:** Theo dõi tất cả các bước phê duyệt (Ai, Khi nào, Hành động) phục vụ mục đích kiểm toán. |

---

## II. Functional Requirements

### 1. Main Features (Group 6 Asset Modules)
Based on the defined scope, the system includes 10 core modules:

#### 1.1 Core Inventory Modules (Mandatory)
- **M01. Asset Category Management:** Manage classification (Furniture, IT, Lab Equipment).
- **M02. Asset Management:** Detailed ledger of every asset (Batch or Single item tracking).
- **M05. Asset Inventory Addition:** Recording new arrivals from the Ministry.
- **M08. Asset Transfer/Movement:** Moving assets between rooms or departments.
- **M09. Asset Allocation/Handover:** Assigning responsibility to specific personnel/rooms.
- **M10. Reports and Statistics:** Dashboard and PDF/Excel export for school leadership.

#### 1.2 Extended Workflow Modules (Optional/Added)
- **M03. Provisioning Request Process:** Collecting needs and generating the Ministry request form.
- **M04. Asset Intake Process:** Structured verification of goods arriving from external sources.
- **M06. Maintenance/Repair Process:** Tracking the repair lifecycle including Guest QR reporting.
- **M07. Asset Return/Liquidation Process:** Formal process for returning items to the Ministry or decommissioning.

### 2. User Authentication / Xác thực người dùng
- **F01. Login/Logout:** **EN:** Secure access for defined roles. <br> **VN:** Truy cập bảo mật cho các vai trò đã được xác định.
- **F02. Profile Management:** **EN:** Users can update their personal information. <br> **VN:** Người dùng có thể cập nhật thông tin cá nhân.

### 3. System Administration (Standard RDS Requirements) / Quản trị hệ thống
#### 3.1 Master Data (Settings) / Dữ liệu danh mục (Cài đặt)
- **F03. Setting List:** **EN:** View, filter, and search master data (e.g., Asset Statuses, Room Types, Roles). <br> **VN:** Xem, lọc và tìm kiếm dữ liệu danh mục (ví dụ: Trạng thái tài sản, Loại phòng, Vai trò).
- **F04. Setting Details:** **EN:** Add/Update master data entries, including setting priority and status (Active/Inactive). <br> **VN:** Thêm/Cập nhật các mục dữ liệu danh mục, bao gồm thứ tự ưu tiên và trạng thái (Hoạt động/Ngừng hoạt động).

#### 3.2 User Management / Quản lý người dùng
- **F05. User List:** **EN:** Administrator can view and manage all system users (Principal, Staff, etc.). <br> **VN:** Quản trị viên có thể xem và quản lý tất cả người dùng hệ thống (Hiệu trưởng, Nhân viên, v.v.).
- **F06. Role Authorization:** **EN:** Assigning permissions to specific modules based on organizational structure. <br> **VN:** Phân quyền cho từng mô-đun cụ thể dựa trên cấu trúc tổ chức.

---

## III. System Design

### 1. Context Diagram (Data Flow)
- **Guest** -> [Damage Description, Photo] -> **System**
- **Facilities Staff** -> [Inventory Data, Allocation Update, Verification] -> **System**
- **Vice Principal** -> [Approval Commands] -> **System**
- **Principal** -> [Final Approval, Report Request] -> **System**
- **System** -> [PDF Asset Forms, Handover Receipts, Dashboard Charts] -> **Users**

### 2. Database Design (Entity Relationships)
- **Entities:** `Users`, `Roles`, `Settings`, `Assets`, `Categories`, `Rooms`, `Allocations`, `Transfers`, `MaintenanceRequests`.

---
*This document aligns with the Group6 RDS structuring requirements and the provided project scope.*
