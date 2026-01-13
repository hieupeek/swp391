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

### 1.2 Operational Insights / Phân tích quy trình thực tế
The system logic is built upon the following real-world operational patterns / Logic hệ thống được xây dựng dựa trên các đặc thù vận hành thực tế sau:

#### A. Office Supplies / Nhóm Văn phòng phẩm (Consumables)
- **Requesting (Đề xuất):** Facilities staff aggregates quantity needs from all departments -> Principal reviews and prints the official request form for the Ministry. / (Bộ phận CSVC tổng kết số lượng -> Hiệu trưởng duyệt và in đơn gửi Bộ).
- **Intake (Tiếp nhận):** Upon delivery, facilities staff conducts an on-site inventory check -> Finalize quantities -> Sign the official handover record. / (Khi Bộ cấp xuống, nhân viên CSVC kiểm kê tại chỗ -> Chốt số lượng -> Ký biên bản bàn giao).
- **Allocation (Cấp phát):** Assets are distributed based on the initial collected needs or divided equally among teachers. / (Chia đều cho giáo viên bộ môn hoặc đáp ứng theo nhu cầu đã thu thập ban đầu).

#### B. Fixed Assets / Nhóm Tài sản cố định (Furniture & Electronics)
- **Provisioning (Cung ứng):** Focuses on "Ministry Provisioning" (direct grants) for simplicity. / (Lựa chọn phương án xin Bộ cấp trực tiếp để tối ưu quy trình).
- **Quantity Logic (Logic số lượng):** 
    - **Tables/Chairs:** Often requested in surplus for maintenance/replacement purposes. / (Bàn ghế thường xin dư ra để dự phòng thay thế).
    - **Electronics (TVs/Projectors):** Strictly matched 1:1 with the number of classrooms/rooms. / (Tivi, máy chiếu cấp đúng theo số lượng phòng học).

#### C. Liquidation & Repair / Thanh lý và Sửa chữa
- **Liquidation (Thanh lý):** Assets are packed and sent back to the Ministry for resale or disposal. / (Đóng gói gửi về Bộ nếu muốn thanh lý/bán).
- **Maintenance (Bảo trì):** If the asset is still salvageable, an internal repair process is initiated. / (Nếu vẫn dùng được thì ưu tiên quy trình sửa chữa).

### 2. External Entities / Các đối tượng liên quan
| # | Entity | Mô tả (Description) |
|---|--------|-------------|
| 1 | **Guest** (Khách) | **EN:** Anonymous users (Students/Visitors/Teachers) who report damaged property via QR codes. <br> **VN:** Người dùng ẩn danh (Học sinh/Khách/Giáo viên) báo cáo hỏng hóc qua mã QR. |
| 2 | **Facilities Staff** (NV Thiết bị) | **EN:** Operational users who manage inventory, verify reports, and handle physical asset movements. <br> **VN:** Người vận hành chính: quản lý kho, xác minh báo cáo và điều chuyển tài sản. |
| 3 | **Vice Principal** (Phó Hiệu trưởng) | **EN:** Secondary approver and internal overseer of facility status and maintenance requests. <br> **VN:** Người kiểm duyệt cấp 1, giám sát tình trạng cơ sở vật chất và các yêu cầu bảo trì. |
| 4 | **Principal** (Hiệu trưởng) | **EN:** High-level approver for provisioning requests and consumer of executive reports. <br> **VN:** Người phê duyệt cao nhất cho các đề xuất mua sắm và theo dõi báo cáo tổng hợp. |

### 3. Business Processes / Quy trình nghiệp vụ
The system follows the "Provisioning & Handover" model / Hệ thống tuân theo mô hình "Cung ứng & Bàn giao". Below are detailed steps for diagramming (Activity/BPMN):

#### 3.1 Provisioning Flow / Quy trình Cung ứng (Annual Needs)
1. **Facilities Staff:** Collects requests from rooms/departments and inputs them into AMS. / (Thu thập nhu cầu từ các phòng ban và nhập vào hệ thống).
2. **Vice Principal:** Reviews the summary and provides initial approval or requests revisions. / (Xem xét danh sách tổng hợp, phê duyệt sơ bộ hoặc yêu cầu chỉnh sửa).
3. **Principal:** Reviews the final proposal and provides formal digital approval. / (Xem xét đề xuất cuối cùng và phê duyệt chính thức trên hệ thống).
4. **System:** Auto-generates a standardized Ministry Request PDF form. / (Tự động tạo mẫu đơn đề xuất gửi Bộ dưới dạng PDF).
5. **Principal:** Signs the physical/digital document to send to the Ministry. / (Ký văn bản để gửi lên Bộ).

#### 3.2 Intake Flow / Quy trình Tiếp nhận (From Ministry)
1. **Ministry:** Delivers physical assets along with handover documentation. / (Bộ bàn giao tài sản thực tế kèm theo biên bản bàn giao).
2. **Facilities Staff:** Performs physical verification (Quantity/Quality) against the documentation. / (Nhân viên thiết bị kiểm tra thực tế số lượng/chất lượng so với biên bản).
3. **Facilities Staff:** Inputs asset data into AMS (M05 - Inventory Addition). / (Nhập dữ liệu tài sản vào hệ thống - Mô-đun M05).
4. **System:** Generates unique QR codes for each asset/batch. / (Hệ thống tạo mã QR duy nhất cho từng tài sản hoặc lô tài sản).
5. **Facilities Staff:** Prints QR labels and performs physical allocation (M04). / (In nhãn QR và thực hiện bàn giao/cấp phát thực tế).

#### 3.3 Maintenance Flow / Quy trình Bảo trì (Damage Reporting)
1. **Guest/Staff:** Scans the asset's QR code and submits a damage report (Photo + Description). / (Quét mã QR của tài sản và gửi báo cáo hỏng hóc kèm ảnh + mô tả).
2. **System:** Records the request and notifies the Facilities Staff. / (Ghi nhận yêu cầu và thông báo cho Nhân viên thiết bị).
3. **Facilities Staff:** On-site verification of the reported issue. / (Xác minh thực tế tình trạng hỏng hóc).
4. **Vice Principal:** Reviews the verification and approves the repair budget/plan. / (Xem xét xác minh và phê duyệt kế hoạch/kinh phí sửa chữa).
5. **Facilities Staff:** Executes the repair and updates the result in AMS. / (Thực hiện sửa chữa và cập nhật kết quả lên hệ thống).

#### 3.4 Liquidation/Return Flow / Quy trình Thanh lý/Thu hồi
1. **Facilities Staff:** Identifies unrepairable/obsolete assets and updates status to "Pending Liquidation". / (Xác định tài sản hỏng không thể sửa hoặc hết hạn, cập nhật trạng thái "Chờ thanh lý").
2. **Principal:** Reviews the liquidation list and provides official approval. / (Xem xét danh sách thanh lý và phê duyệt chính thức).
3. **System:** Generates a Return Handover Document. / (Hệ thống tạo Biên bản bàn giao hoàn trả).
4. **Facilities Staff:** Physically returns items to the Ministry and obtains a receipt. / (Hoàn trả tài sản thực tế về Bộ và nhận biên bản xác nhận).
5. **Facilities Staff:** Updates AMS status to "Returned/Disposed" and attaches the scanned receipt. / (Cập nhật trạng thái "Đã hoàn trả/Thanh lý" và đính kèm minh chứng chứng từ).

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

### 1. Context Diagram (Data Flow) / Sơ đồ ngữ cảnh (Luồng dữ liệu)
Below are the detailed interactions between external actors and the System / Chi tiết các tương tác giữa tác nhân bên ngoài và Hệ thống:

#### 1.1 Guest (Khách/Người dùng)
- **Actor -> System:** Incident Report (Photo, Description) / Báo cáo sự cố (Ảnh, mô tả).
- **System -> Actor:** Submission Confirmation / Xác nhận đã gửi báo cáo thành công.

#### 1.2 Facilities Staff (Nhân viên thiết bị)
- **Actor -> System:** 
    - Asset Allocation/Transfer details / Chi tiết phân bổ và điều chuyển tài sản.
    - Maintenance Logs / Nhật ký bảo trì và kết quả sửa chữa.
    - Inventory Data / Dữ liệu kiểm kê thực tế.
    - Damage Verification / Kết quả xác minh hư hỏng.
- **System -> Actor:** 
    - New Incident Notifications / Thông báo về báo cáo sự cố mới.
    - Inventory Documents/Reports / Các tài liệu, biên bản và báo cáo kiểm kê.
    - Approval/Rejection Notifications / Thông báo phê duyệt hoặc từ hồi yêu cầu.

#### 1.3 Vice Principal (Phó Hiệu trưởng)
- **Actor -> System:** Approval/Rejection for Provisioning and Maintenance / Phê duyệt hoặc từ chối các yêu cầu cung ứng và bảo trì.
- **System -> Actor:** 
    - Inventory Status Summaries / Báo cáo tóm tắt tình trạng kho và tài sản.
    - Pending Request Lists / Danh sách các yêu cầu đang chờ xử lý.

#### 1.4 Principal (Hiệu trưởng)
- **Actor -> System:** Final Approval (Final sign-off) / Phê duyệt cuối cùng (Ký duyệt chính thức).
- **System -> Actor:** 
    - Generated PDF Request Forms / Các mẫu đơn đề xuất đã được tạo dưới dạng PDF.
    - Pending Requests for Approval / Danh sách các yêu cầu cấp cao chờ phê duyệt.
    - Statistical Dashboards & Asset Reports / Bảng điều khiển thống kê và báo cáo tài sản cấp cao.
    - Asset Audit/Status Summary / Tóm tắt tình trạng và kiểm toán tài sản.

### 2. Database Design (Entity Relationships)
- **Entities:** `Users`, `Roles`, `Settings`, `Assets`, `Categories`, `Rooms`, `Allocations`, `Transfers`, `MaintenanceRequests`.

---
*This document aligns with the Group6 RDS structuring requirements and the provided project scope.*
