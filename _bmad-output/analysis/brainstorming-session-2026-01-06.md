---
selected_approach: 'progressive-flow'
techniques_used: ['What If Scenarios', 'Mind Mapping', 'SCAMPER Method', 'Solution Matrix']
stepsCompleted: [1, 2]
---

# Brainstorming Session Results

**Facilitator:** swp391
**Date:** 2026-01-06

## Session Overview

**Topic:** Hệ thống Quản lý Tài sản (Asset Management System)
**Goals:** Phân tích chi tiết nghiệp vụ và luồng xử lý cho 10 module chức năng, đặc biệt là các quy trình mua sắm, bảo trì và thanh lý.

### Context Guidance

Dựa trên tài liệu bối cảnh dự án, chúng ta sẽ tập trung vào:
- **Nghiệp vụ chi tiết:** Luồng xử lý cho từng quy trình (Mua sắm, Bảo trì, Thanh lý...).
- **Cấu trúc dữ liệu:** Cách lưu trữ trong MySQL để đảm bảo tính nhất quán.
- **Giao diện người dùng:** Cách thể hiện bằng JSP/Servlet.

### Session Setup

Người dùng đã cung cấp danh sách 10 tính năng cụ thể với sự phân chia giữa tính năng bắt buộc và tùy chọn (optional).
1. Quản lý danh mục tài sản
2. Quản lý tài sản
3. Quy trình lập kế hoạch mua sắm
4. Quy trình mua sắm tài sản
5. Ghi nhận tăng tài sản
6. Quy trình bảo trì/sửa chữa tài sản
7. Quy trình thanh lý tài sản
8. Điều chuyển tài sản
9. Cấp phát tài sản
10. Báo cáo thống kê
mục 3,4,6,7 là các option làm thêm, còn lại là bắt buộc


## Technique Selection

**Approach:** Progressive Technique Flow
**Journey Design:** Phát triển hệ thống từ khám phá ý tưởng đến lập kế hoạch triển khai cụ thể.

**Progressive Techniques:**

- **Phase 1 - Exploration:** **What If Scenarios** để tìm ra các kịch bản ngoại lệ và luồng nghiệp vụ phức tạp.
- **Phase 2 - Context Analysis (3 Steps):** Xác định Roles, Tasks và Context Diagram để định hình luồng dữ liệu thực tế.
- **Phase 3 - Pattern Recognition:** **Mind Mapping** để tổ chức dữ liệu và các mối quan hệ thực thể.
- **Phase 4 - Development:** **SCAMPER Method** để tối ưu hóa và làm chi tiết từng tính năng.
- **Phase 5 - Action Planning:** **Solution Matrix** để chốt hạ kiến trúc Database và Servlet/JSP.

**Journey Rationale:** Bắt đầu bằng việc đặt câu hỏi cho những gì hiện hữu, sau đó cấu trúc lại, làm sắc nét chi tiết và cuối cùng là tạo bản vẽ kỹ thuật để bắt đầu code.

## Context Analysis (3 Steps)

### B1: Xác định Cấu trúc Tổ chức (User Roles)

Dựa trên khảo sát thực tế tại Trường công, hệ thống có các nhóm người dùng:


1.  **Cán bộ Cơ sở vật chất (CSVC/Admin):** Người vận hành chính. Tổng hợp nhu cầu, kiểm kê hàng về từ Bộ, phân bổ tài sản, quản lý kho dự phòng.
2.  **Hiệu trưởng (Approver):** Người có quyền quyết định cao nhất. Duyệt các "Bảng dự trù" để gửi lên Bộ xin cấp.
3.  **Giáo viên/Trưởng bộ môn (User):** Người sử dụng cuối. Đăng ký nhu cầu cho năm học, nhận bàn giao tài sản và báo hỏng (khi đăng nhập).
4.  **Khách/Học sinh (Guest):** Người dùng vãng lai, không cần tài khoản. Báo hỏng tài sản thông qua việc quét mã QR Code dán trên thiết bị/phòng học.

### B2: Xác định User Task (Nhiệm vụ người dùng)

**1. Khách/Học sinh (Guest):**
*   *Task 1:* Quét QR Code để truy cập form báo hỏng nhanh.
*   *Task 2:* Gửi thông tin sự cố (kèm ảnh) mà không cần đăng nhập.

**2. Giáo viên/Trưởng bộ môn:**
*   *Task 1:* Đăng ký nhu cầu CSVC cho năm học mới.
*   *Task 2:* Ký xác nhận bàn giao tài sản (trên hệ thống).
*   *Task 3:* Theo dõi tình trạng các yêu cầu sửa chữa mình đã gửi.

**3. Cán bộ CSVC:**
*   *Task 1:* Tổng hợp nhu cầu từ GV thành "Bảng dự trù tổng".
*   *Task 2:* Nhập kho tài sản (dựa trên Biên bản bàn giao từ Bộ).
*   *Task 3:* Phân bổ/Cấp phát tài sản cho các lớp/phòng.
*   *Task 4:* Quản lý kho dự phòng và luân chuyển tài sản thay thế.
*   *Task 5:* Lập danh sách thanh lý/trả về Bộ.
*   *Task 6:* Kiểm duyệt các tin báo hỏng từ Guest (Xác thực xem có đúng là hỏng thật không).

**4. Hiệu trưởng:**
*   *Task 1:* Xem và Phê duyệt (Approve) Bảng dự trù.
*   *Task 2:* Xem các báo cáo thống kê tình trạng tài sản.

### B3: Context Diagram (Sơ đồ ngữ cảnh & Luồng dữ liệu)

Mô tả luồng dữ liệu vào/ra (Input/Output) giữa Người và Hệ thống:

*   **Tương tác 1: Khách/Học sinh -> Hệ thống (New Feature)**
    *   *Input:* Form báo hỏng vãng lai (Ảnh, Mô tả tình trạng).
    *   *Output:* Thông báo "Gửi thành công, chờ xác nhận".

*   **Tương tác 2: Giáo viên -> Hệ thống**
    *   *Input:* Form đăng ký nhu cầu, Form báo hỏng (có danh danh).
    *   *Output:* Thông báo kết quả xử lý, Lịch sử sửa chữa.

*   **Tương tác 3: Cán bộ CSVC -> Hệ thống**
    *   *Input:* Số lượng thực nhận (Nhập tay từ giấy tờ Bộ), Duyệt tin báo hỏng (Verify), Cập nhật kết quả sửa chữa.
    *   *Output:* File PDF "Đơn xin cấp CSVC" (để trình ký), Báo cáo kiểm kê.

*   **Tương tác 4: Hiệu trưởng -> Hệ thống**
    *   *Input:* Lệnh Duyệt dự trù (Click Approve).
    *   *Output:* Báo cáo tổng hợp Dashboard.

*(Lưu ý: Hệ thống KHÔNG kết nối trực tiếp với Bộ Giáo dục. Việc gửi đơn và nhận hàng là quy trình thủ công bên ngoài, hệ thống chỉ hỗ trợ xuất file và nhập liệu).*

