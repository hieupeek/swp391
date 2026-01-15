# HỆ THỐNG QUẢN LÝ TÀI SẢN THIẾT BỊ TRƯỜNG HỌC

## CÁC ACTOR CHÍNH (5 actors)
*   **Hiệu trưởng/Phó hiệu trưởng** - Quản lý cấp cao, phê duyệt mua sắm lớn, giám sát hệ thống
*   **Trưởng phòng Tài chính - Kế toán** - Quản lý tài sản, quản lý hệ thống
*   **Nhân viên quản lý tài sản** - Thực hiện các nghiệp vụ quản lý tài sản hàng ngày, phê duyệt yêu cầu cấp phát
*   **Trưởng/Phụ trách bộ môn** - Yêu cầu và quản lý tài sản cho bộ môn/phòng học
*   **Giáo viên** - Sử dụng và báo cáo tình trạng tài sản

---

## NHÓM 1: QUẢN LÝ DANH MỤC TÀI SẢN (4 use cases)

### UC01: Tạo danh mục tài sản mới
*   **Actor:** Trưởng phòng TC-KT
*   **Mô tả:** Tạo danh mục phân loại tài sản (Thiết bị dạy học, Máy tính, Đồ dùng văn phòng, Thiết bị phòng thí nghiệm...)

### UC02: Cập nhật thông tin danh mục
*   **Actor:** Trưởng phòng TC-KT
*   **Mô tả:** Chỉnh sửa tên, mô tả, thuộc tính của danh mục tài sản

### UC03: Xóa danh mục tài sản
*   **Actor:** Trưởng phòng TC-KT
*   **Mô tả:** Xóa danh mục không còn sử dụng (kiểm tra không có tài sản nào thuộc danh mục)

### UC04: Xem và tìm kiếm danh sách danh mục
*   **Actor:** Tất cả các actor
*   **Mô tả:** Hiển thị danh sách các danh mục tài sản có phân cấp, tìm kiếm và lọc theo từ khóa, mã danh mục

---

## NHÓM 2: QUẢN LÝ TÀI SẢN (6 use cases)

### UC05: Thêm mới tài sản vào hệ thống
*   **Actor:** Nhân viên quản lý tài sản
*   **Mô tả:** Nhập thông tin tài sản mới, hệ thống tự động tạo và gán mã tài sản theo danh mục (VD: MT-2024-001)

### UC06: Xem và tìm kiếm danh sách tài sản
*   **Actor:** Tất cả các actor
*   **Mô tả:** Xem danh sách tài sản, tìm kiếm và lọc theo mã, tên, danh mục, vị trí, trạng thái

### UC07: Xem chi tiết tài sản
*   **Actor:** Tất cả các actor
*   **Mô tả:** Xem đầy đủ thông tin, lịch sử điều chuyển, vị trí hiện tại của tài sản

### UC08: Cập nhật thông tin tài sản
*   **Actor:** Nhân viên quản lý tài sản
*   **Mô tả:** Chỉnh sửa thông tin chi tiết, giá trị tài sản

### UC09: Cập nhật trạng thái tài sản
*   **Actor:** Nhân viên quản lý tài sản, Giáo viên
*   **Mô tả:** Thay đổi trạng thái (Mới, Đang sử dụng, Hỏng, Bảo trì, Thanh lý), báo cáo tình trạng

### UC10: Xóa/Thanh lý tài sản
*   **Actor:** Trưởng phòng TC-KT
*   **Mô tả:** Đánh dấu tài sản thanh lý, hỏng không sử dụng được

---

## NHÓM 3: GHI NHẬN TĂNG TÀI SẢN (9 use cases)

### UC11: Tạo yêu cầu cấp phát tài sản
*   **Actor:** Trưởng bộ môn, Giáo viên
*   **Mô tả:** Gửi yêu cầu cần tài sản cho phòng học/bộ môn

### UC12: Xem danh sách yêu cầu cấp phát tài sản
*   **Actor:** Nhân viên quản lý tài sản, Trưởng phòng TC-KT, Trưởng bộ môn, Giáo viên
*   **Mô tả:** Xem các yêu cầu cấp phát tài sản theo trạng thái (chờ duyệt, đã duyệt, từ chối)

### UC13: Kiểm tra tồn kho tài sản
*   **Actor:** Nhân viên quản tài sản
*   **Mô tả:** Kiểm tra xem tài sản được yêu cầu có sẵn trong kho không

### UC14: Phê duyệt/Từ chối yêu cầu cấp phát tài sản
*   **Actor:** Nhân viên quản lý tài sản
*   **Mô tả:** Xét duyệt hoặc từ chối các yêu cầu cấp phát tài sản (kèm lý do nếu từ chối)

### UC15: Hủy yêu cầu cấp phát tài sản
*   **Actor:** Trưởng bộ môn, Giáo viên (người tạo yêu cầu)
*   **Mô tả:** Hủy yêu cầu cấp phát đã tạo khi chưa được phê duyệt

### UC16: Tạo yêu cầu mua sắm tài sản
*   **Actor:** Nhân viên quản lý tài sản
*   **Mô tả:** Lập đề xuất mua sắm khi không có tài sản trong kho để đáp ứng yêu cầu

### UC17: Xem danh sách yêu cầu mua sắm tài sản
*   **Actor:** Nhân viên quản lý tài sản, Trưởng phòng TC-KT, Hiệu trưởng/Phó hiệu trưởng
*   **Mô tả:** Xem các yêu cầu mua sắm theo trạng thái (chờ duyệt, đã duyệt, từ chối)

### UC18: Phê duyệt/Từ chối yêu cầu mua sắm
*   **Actor:** Hiệu trưởng/Phó hiệu trưởng
*   **Mô tả:** Phê duyệt hoặc từ chối đề xuất mua sắm tài sản mới (đặc biệt với giá trị lớn), kèm lý do nếu từ chối

### UC19: Hủy yêu cầu mua sắm tài sản
*   **Actor:** Nhân viên quản lý tài sản (người tạo yêu cầu)
*   **Mô tả:** Hủy yêu cầu mua sắm đã tạo khi chưa được phê duyệt

---

## NHÓM 4: ĐIỀU CHUYỂN TÀI SẢN (6 use cases)

### UC20: Tạo phiếu điều chuyển tài sản
*   **Actor:** Nhân viên quản lý tài sản
*   **Mô tả:** Lập phiếu chuyển tài sản từ phòng này sang phòng khác

### UC21: Xem và tìm kiếm phiếu điều chuyển
*   **Actor:** Nhân viên quản lý tài sản, Trưởng phòng TC-KT, Trưởng bộ môn
*   **Mô tả:** Xem lịch sử các phiếu điều chuyển, tìm kiếm và lọc theo thời gian, phòng, tài sản, trạng thái

### UC22: Phê duyệt/Từ chối điều chuyển tài sản
*   **Actor:** Trưởng phòng TC-KT
*   **Mô tả:** Xét duyệt hoặc từ chối phiếu điều chuyển (kèm lý do nếu từ chối)

### UC23: Xác nhận bàn giao tài sản
*   **Actor:** Trưởng bộ môn
*   **Mô tả:** Xác nhận bàn giao tài sản khi chuyển đi từ phòng/bộ môn mình

### UC24: Xác nhận nhận tài sản
*   **Actor:** Trưởng bộ môn
*   **Mô tả:** Xác nhận đã nhận tài sản tại vị trí mới

### UC25: Hủy phiếu điều chuyển
*   **Actor:** Nhân viên quản lý tài sản, Trưởng phòng TC-KT
*   **Mô tả:** Hủy phiếu điều chuyển chưa thực hiện hoặc chưa được phê duyệt

---

## NHÓM 5: BÁO CÁO VÀ PHÂN TÍCH (2 use cases)

### UC26: Xem báo cáo tài sản
*   **Actor:** Hiệu trưởng/Phó hiệu trưởng, Trưởng phòng TC-KT, Nhân viên quản lý tài sản, Trưởng bộ môn
*   **Mô tả:** Xem các loại báo cáo với khả năng lọc và tùy chỉnh theo quyền hạn:

1.  **Hiệu trưởng/Phó hiệu trưởng:**
    *   Báo cáo tổng quan tài sản (dashboard): thống kê tổng số lượng, giá trị, phân bổ theo danh mục/phòng/trạng thái
    *   Báo cáo yêu cầu và mua sắm: tổng hợp yêu cầu cấp phát, mua sắm theo thời gian và trạng thái
2.  **Trưởng phòng TC-KT:**
    *   Báo cáo tổng quan tài sản (dashboard): thống kê tổng số lượng, giá trị, phân bổ theo danh mục/phòng/trạng thái
    *   Báo cáo tài sản chi tiết: theo danh mục, phòng học, trạng thái, nguồn kinh phí (có filter)
    *   Báo cáo yêu cầu và mua sắm: tổng hợp yêu cầu cấp phát, mua sắm theo thời gian và trạng thái
    *   Báo cáo điều chuyển: lịch sử điều chuyển tài sản theo thời gian, phòng ban
3.  **Nhân viên quản lý tài sản:**
    *   Báo cáo tài sản chi tiết: theo danh mục, phòng học, trạng thái (có filter)
    *   Báo cáo yêu cầu và mua sắm: tổng hợp yêu cầu cấp phát, mua sắm theo thời gian và trạng thái
    *   Báo cáo điều chuyển: lịch sử điều chuyển tài sản
4.  **Trưởng bộ môn:**
    *   Báo cáo tài sản bộ môn: tài sản thuộc bộ môn theo phòng học, trạng thái
    *   Báo cáo yêu cầu: lịch sử yêu cầu cấp phát của bộ môn

### UC27: Xuất báo cáo ra file
*   **Actor:** Hiệu trưởng/Phó hiệu trưởng, Trưởng phòng TC-KT, Nhân viên quản lý tài sản, Trưởng bộ môn
*   **Mô tả:** Xuất các báo cáo đã xem/lọc ra file Excel, PDF để lưu trữ hoặc trình bày (chỉ xuất được các báo cáo mà actor có quyền xem)

---

## NHÓM 6: COMMON (5 use cases)

### UC28: Đăng nhập hệ thống
*   **Actor:** Tất cả các actor
*   **Mô tả:** Xác thực tài khoản bằng username/email và mật khẩu để truy cập hệ thống

### UC29: Đăng xuất hệ thống
*   **Actor:** Tất cả các actor
*   **Mô tả:** Kết thúc phiên làm việc và thoát khỏi hệ thống

### UC30: Quản lý hồ sơ cá nhân
*   **Actor:** Tất cả các actor
*   **Mô tả:** Xem, cập nhật thông tin cá nhân (họ tên, email, số điện thoại, ảnh đại diện...)

### UC31: Quên mật khẩu
*   **Actor:** Tất cả các actor
*   **Mô tả:** Yêu cầu khôi phục mật khẩu qua email khi quên mật khẩu

### UC32: Đổi mật khẩu
*   **Actor:** Tất cả các actor
*   **Mô tả:** Thay đổi mật khẩu hiện tại (yêu cầu nhập mật khẩu cũ để xác thực)

---

## GHI CHÚ

**Quản lý Danh mục vs Quản lý Tài sản:**
*   **Danh mục:** Là các loại/nhóm tài sản (VD: Máy tính, Bàn ghế, Thiết bị thí nghiệm) - đây là template/khuôn mẫu
*   **Tài sản:** Là các thiết bị cụ thể thuộc danh mục (VD: Máy tính Dell Latitude E7450 - mã MT-2024-001)
*   **Mã tài sản tự động:** Khi thêm tài sản mới (UC05), hệ thống tự động tạo mã dựa theo quy tắc của danh mục

**Quy trình Yêu cầu Tài sản:**
1.  Giáo viên/Trưởng bộ môn tạo yêu cầu cấp phát (UC11)
2.  Nhân viên quản lý tài sản kiểm tra tồn kho (UC13)
3.  Nếu có: Nhân viên phê duyệt và cấp phát (UC14)
4.  Nếu không có: Nhân viên tạo yêu cầu mua sắm (UC16)
5.  Hiệu trưởng/Phó hiệu trưởng phê duyệt mua sắm (UC18)

**Phân quyền theo cấp:**
*   **Hiệu trưởng/Phó hiệu trưởng:** Phê duyệt mua sắm lớn, xem các báo cáo chiến lược
*   **Trưởng phòng TC-KT:** Quản lý danh mục, thanh lý tài sản, phê duyệt điều chuyển, quản lý hệ thống
*   **Nhân viên quản lý tài sản:** Thực hiện nghiệp vụ hàng ngày, phê duyệt yêu cầu cấp phát
*   **Trưởng bộ môn:** Yêu cầu và quản lý tài sản cấp bộ môn, xác nhận điều chuyển
*   **Giáo viên:** Yêu cầu tài sản, báo cáo tình trạng
