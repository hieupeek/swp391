# UI MOCKUP SPECIFICATION - ASSET MANAGEMENT SYSTEM (AMS)

**Version:** 1.0  
**Date:** 2026-01-22  
**Based on:** Vision & Scope 2.0, Database Schema Final

---

## 1. NAVIGATION STRUCTURE (SITEMAP)

Hệ thống sử dụng bố cục **Admin Dashboard Layout**: Sidebar bên trái (Menu), Header bên trên (User info, Notification), và Content Area ở giữa.

### Menu Items (Phân quyền Dynamic)
*   **Dashboard** (All Roles - View khác nhau)
*   **Quản lý Tài sản (Asset Mgmt)** (Finance, Staff, HOD*)
    *   Danh sách tài sản (Asset List)
    *   Nhập tài sản mới (Import/Add New) - *Staff/Finance only*
    *   Danh mục (Categories) - *Finance only*
*   **Mua sắm & Cấp phát (Acquisition)**
    *   Yêu cầu của tôi (My Requests) - *All*
    *   Duyệt yêu cầu (Approve Requests) - *Finance, Principal, HOD*
    *   Kế hoạch mua sắm (Procurement Plans) - *Finance, Principal*
*   **Điều chuyển (Transfer)**
    *   Phiếu điều chuyển (Transfer Tickets) - *Staff, Finance*
    *   Nhiệm vụ của tôi (My Tasks - Handover/Receive) - *HOD*
*   **Bảo trì & Thanh lý**
    *   Yêu cầu bảo trì (Maintenance)
    *   Biên bản thanh lý (Liquidation) - *Staff, Principal*
*   **Báo cáo (Reports)** - *Principal, Finance, HOD*
*   **Hệ thống (System)**
    *   Người dùng (Users) - *Finance*
    *   Settings

---

## 2. COMMON SCREENS (MÀN HÌNH CHUNG)

### 2.1. Login Screen
*   **Layout:** Center Card, Background mờ ảnh trường học.
*   **Fields:**
    *   Input: `Email / Username`
    *   Input: `Password` (Type: Password, icon con mắt show/hide)
*   **Actions:**
    *   Button: `Đăng nhập` (Primary Color)
    *   Link: `Quên mật khẩu?`

---

## 3. DASHBOARDS (THEO ROLE)

### 3.1. Principal Dashboard (Executive View)
*   **Top Cards (KPIs):**
    *   `Tổng giá trị tài sản`: 5.2 Tỷ VNĐ
    *   `Ngân sách năm nay`: Đã dùng 45%
    *   `Tài sản chờ thanh lý`: 15 món
*   **Chart Section:**
    *   Bar Chart: Chi tiêu mua sắm theo quý.
    *   Pie Chart: Tỷ lệ tài sản theo trạng thái (Đang dùng, Hỏng, Mới).
*   **Action List (Cần duyệt ngay):**
    *   Table: "Yêu cầu mua sắm > 50tr" hoặc "Kế hoạch quý 1".
    *   Columns: Tên kế hoạch | Tổng tiền | Người tạo | Hành động (Button: Xem ngay).

### 3.2. Finance Head Dashboard
*   **Top Cards:**
    *   `Yêu cầu chờ duyệt`: 12
    *   `Tổng tài sản nhập mới trong tháng`: +20
    *   `Báo hỏng chờ chi tiền`: 3
*   **Main Section:**
    *   Tab 1: Danh sách Request mới nhất.
    *   Tab 2: Tài sản sắp hết khấu hao.

### 3.3. Asset Staff Dashboard (Operational View)
*   **Top Cards:**
    *   `Phiếu điều chuyển chưa xong`: 5
    *   `Yêu cầu bảo trì mới`: 2
*   **Task List (Quan trọng):**
    *   Table: Asset cần dán tem, Phiếu chuyển cần thực hiện.

---

## 4. ASSET MANAGEMENT SCREENS

### 4.1. Asset List (Danh sách tài sản)
*   **Filter Bar (Trên cùng):**
    *   Search Box: "Tìm theo tên, mã tài sản..."
    *   Dropdown: `Trạng thái` (All, In Use, Broken...)
    *   Dropdown: `Phòng/Khoa`
    *   Dropdown: `Danh mục`
*   **Data Table:**
    *   Columns: [Hình ảnh (Thumb)] | Mã TS | Tên TS | Danh mục | Vị trí | Giá trị | Trạng thái | Hành động
    *   Status Badge: Màu xanh (In Use), Đỏ (Broken), Xám (Liquidated).
*   **Actions:**
    *   Button (Top Right): `+ Thêm mới` (Mở Modal hoặc sang trang Import).
    *   Row Actions: `Xem chi tiết` (Icon Eye), `Sửa` (Icon Pencil), `Lịch sử` (Icon Clock).

### 4.2. Asset Detail & Lifecycle (Chi tiết tài sản)
*   **Header:**
    *   Title: Tên tài sản + Badge Trạng thái.
    *   Buttons: `Cập nhật trạng thái`, `Báo hỏng`, `Yêu cầu thanh lý`.
*   **Left Column (Thông tin tĩnh):**
    *   Hình ảnh lớn.
    *   Thông tin kỹ thuật (Model, Serial, NSX).
    *   Thông tin tài chính (Giá, Ngày mua, BH).
*   **Right Column (Timeline - Lịch sử):**
    *   Vertical Timeline hiển thị quá trình:
        *   *(Nay)*: Đang ở Phòng 101.
        *   *(Hôm qua)*: Bảo trì xong bởi Staff A.
        *   *(Tuần trước)*: Báo hỏng bởi HOD Lý.
        *   *(2024)*: Nhập kho.

---

## 5. ACQUISITION SCREENS (MUA SẮM)

### 5.1. Create Request (HOD View)
*   **Layout:** Form Wizard.
*   **Section 1: Thông tin chung:**
    *   Lý do yêu cầu (Textarea).
    *   Độ ưu tiên (High/Medium/Low).
*   **Section 2: Danh sách thiết bị:**
    *   Table input dynamic (Cho phép thêm dòng).
    *   Row: Chọn Danh mục (Dropdown) | Số lượng (Number) | Ghi chú (Text).
*   **Footer:** Button `Gửi yêu cầu`.

### 5.2. Make Procurement Plan (Finance View)
*   **Concept:** Gom các Request lẻ thành 1 Plan.
*   **Layout:** Split Screen (Chia đôi màn hình).
*   **Left Panel (Pool Requests):**
    *   Danh sách các Allocation Request đang `Pending`.
    *   Checkbox để chọn các Request muốn đưa vào kế hoạch này.
*   **Right Panel (Plan Preview):**
    *   Input: Tên Kế hoạch (VD: Mua sắm đầu năm).
    *   Table: Tổng hợp số lượng (VD: 3 Request xin Laptop -> Tổng 15 Laptop).
    *   Input: Dự trù kinh phí tổng.
*   **Action:** `Tạo kế hoạch & Trình Hiệu trưởng`.

---

## 6. TRANSFER SCREENS (ĐIỀU CHUYỂN)

### 6.1. Create Transfer Ticket (Staff View)
*   **Form:**
    *   Từ phòng: [Dropdown Source]
    *   Đến phòng: [Dropdown Dest]
    *   Ngày chuyển dự kiến: [Datepicker]
*   **Asset Picker:**
    *   List danh sách tài sản đang ở Phòng Source.
    *   Checkbox chọn tài sản cần chuyển.
*   **Action:** `Tạo phiếu`.

### 6.2. My Tasks (HOD View - Handover/Receipt)
*   **Tabs:** `Cần bàn giao` | `Cần nhận`.
*   **Card Item:**
    *   Tiêu đề: "Phiếu #TRF-001: Chuyển 5 PC từ P.Kho sang P.Tin học".
    *   Danh sách đồ.
    *   **Button Action:**
        *   Nếu là HOD Giao: Nút `Xác nhận bàn giao` (Confirm Handover).
        *   Nếu là HOD Nhận: Nút `Xác nhận đã nhận` (Confirm Receipt) - *Chỉ active khi bên Giao đã confirm*.

---

## 7. MAINTENANCE SCREENS

### 7.1. Maintenance Request (Staff Process)
*   **Input:** Chọn tài sản (Auto-suggest).
*   **Issue:** Mô tả lỗi, Upload ảnh hiện trạng.
*   **Status Workflow:**
    *   Chuyển trạng thái: `Reported` -> `In Progress` (Đang sửa).
    *   Form kết thúc: Nhập `Chi phí sửa`, `Ghi chú kỹ thuật`.
    *   Button: `Hoàn thành` -> Auto cập nhật Status Asset về `In Use`.

---

## 8. LIQUIDATION SCENARIO (THANH LÝ)

### 8.1. Create Liquidation Minute (Staff)
*   Tương tự như tạo Procurement Plan nhưng ngược lại.
*   Chọn các tài sản đang ở trạng thái `Broken` hoặc `Expired`.
*   Nhập `Giá trị thu hồi` dự kiến.
*   Tạo biên bản -> Trạng thái `Pending Approval`.

### 8.2. Approve Liquidation (Principal)
*   Xem chi tiết biên bản.
*   Danh sách tài sản đề xuất hủy.
*   Button: `Phê duyệt Thanh lý` -> Hệ thống auto set trạng thái toàn bộ Asset trong list sang `Liquidated` và vô hiệu hóa khỏi kho.

---

**Ghi chú cho Dev UI:**
*   Sử dụng **Bootstrap 5** hoặc **Tailwind CSS** (nếu được phép).
*   Icon set: **FontAwesome** hoặc **Material Icons**.
*   Màu sắc chủ đạo: Xanh Navy (Trust), Cam (Warning/Action).
