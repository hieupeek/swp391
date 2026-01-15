---
title: Vision and Scope Document
project: School Asset Management System (AMS)
version: 1.0
date: 2026-01-15
author: Business Analyst Team
template_source: "Software Requirements, 3rd Edition (Wiegers & Beatty) - Chapter 5"
---

# VISION AND SCOPE DOCUMENT
**Dự án:** Hệ thống Quản lý Tài sản Thiết bị Trường học (AMS)

---

## 1. BUSINESS REQUIREMENTS (YÊU CẦU NGHIỆP VỤ)

### 1.1. Background (Bối cảnh)
Hiện tại, việc quản lý tài sản tại trường Trung học phổ thông đang được thực hiện chủ yếu thông qua sổ sách giấy tờ hoặc các file Excel rời rạc. Việc này dẫn đến nhiều vấn đề:
*   Khó khăn trong việc theo dõi lịch sử luân chuyển tài sản giữa các phòng học và bộ môn.
*   Quy trình báo hỏng, sửa chữa và đề xuất mua mới tốn nhiều thời gian, phải qua nhiều khâu ký duyệt thủ công.
*   Ban giám hiệu (Hiệu trưởng) thiếu cái nhìn tổng quan tức thời về tình trạng cơ sở vật chất để ra quyết định mua sắm hợp lý.
*   Việc kiểm kê cuối kỳ mất nhiều thời gian và độ chính xác không cao do dữ liệu không được cập nhật tập trung.

### 1.2. Business Opportunity (Cơ hội kinh doanh)
Việc triển khai Hệ thống Quản lý Tài sản (AMS) sẽ mang lại cơ hội:
*   **Số hóa toàn diện:** Chuyển đổi quy trình quản lý thủ công sang nền tảng số, giúp dữ liệu được lưu trữ tập trung và nhất quán.
*   **Tối ưu hóa nguồn lực:** Giúp nhà trường tận dụng tối đa tài sản hiện có, tránh mua sắm lãng phí các thiết bị đã có nhưng bị "bỏ quên" trong kho.
*   **Minh bạch hóa:** Mọi quy trình từ yêu cầu cấp phát, phê duyệt mua sắm đến điều chuyển đều được ghi lại trên hệ thống, đảm bảo tính minh bạch và trách nhiệm giải trình.

### 1.3. Business Objectives (Mục tiêu kinh doanh)
Hệ thống cần đạt được các mục tiêu sau trong vòng 6 tháng sau khi triển khai:
*   **BO-1 (Giảm thất thoát):** Giảm tỷ lệ thất thoát tài sản không rõ nguyên nhân xuống dưới 1% thông qua việc theo dõi chặt chẽ lịch sử điều chuyển.
*   **BO-2 (Tăng hiệu quả quy trình):** Giảm thời gian trung bình xử lý một yêu cầu cấp phát/mua sắm từ 3 ngày xuống còn dưới 4 giờ làm việc.
*   **BO-3 (Tiết kiệm thời gian báo cáo):** Giảm 90% thời gian tổng hợp báo cáo kiểm kê định kỳ cho kế toán và ban giám hiệu.
*   **BO-4 (Tối ưu ngân sách):** Tiết kiệm 10% ngân sách mua sắm hằng năm nhờ việc tái sử dụng hiệu quả tài sản và bảo trì kịp thời.

### 1.4. Success Metrics (Chỉ số thành công)
Sự thành công của dự án sẽ được đo lường qua các chỉ số:
*   **SM-1:** 100% tài sản có giá trị (trên 1 triệu VNĐ) được dán mã và quản lý trên hệ thống.
*   **SM-2:** 95% giáo viên và nhân viên sử dụng hệ thống thành thạo để gửi yêu cầu và báo cáo sự cố sau 1 tháng đào tạo.
*   **SM-3:** Hệ thống xuất được báo cáo tổng hợp chính xác ngay lập tức (real-time) thay vì phải chờ tổng hợp thủ công.
*   **SM-4:** Không còn tình trạng trùng lặp mã tài sản hoặc sai lệch thông tin vị trí giữa thực tế và sổ sách.

### 1.5. Vision Statement (Tuyên bố tầm nhìn)
> *"Dành cho Ban giám hiệu, cán bộ quản lý và giáo viên, những người cần quản lý và sử dụng hiệu quả cơ sở vật chất, Hệ thống Quản lý Tài sản Trường học (AMS) là một nền tảng quản lý tập trung, cung cấp khả năng theo dõi toàn bộ vòng đời tài sản từ lúc mua sắm đến khi thanh lý. Khác với quy trình quản lý thủ công bằng Excel hiện tại, sản phẩm của chúng tôi tự động hóa quy trình phê duyệt, cung cấp báo cáo trực quan tức thời và đảm bảo tính chính xác tuyệt đối của dữ liệu kiểm kê, giúp nhà trường tối ưu hóa ngân sách và nâng cao chất lượng phục vụ giảng dạy."*

### 1.6. Business Risks (Rủi ro kinh doanh)
*   **RI-1 (Sự kháng cự của người dùng):** Giáo viên lớn tuổi hoặc nhân viên quen với cách làm cũ có thể ngại sử dụng phần mềm mới.
    *   *Giảm thiểu:* Thiết kế giao diện đơn giản, tổ chức tập huấn kỹ lưỡng.
*   **RI-2 (Dữ liệu ban đầu):** Dữ liệu di trú từ file Excel cũ có thể không sạch (thiếu thông tin, sai mã), dẫn đến sai lệch khi mới vận hành.
    *   *Giảm thiểu:* Có giai đoạn làm sạch dữ liệu và kiểm kê vật lý trước khi nhập liệu chính thức.
*   **RI-3 (Quy trình nội bộ):** Quy trình phê duyệt trên phần mềm có thể cứng nhắc hơn so với linh động thực tế, gây ách tắc nếu người duyệt vắng mặt.
    *   *Giảm thiểu:* Cho phép ủy quyền duyệt hoặc cấu hình quy trình linh hoạt.

### 1.7. Business Assumptions and Dependencies (Giả định và Phụ thuộc)
*   **AS-1:** Giả định rằng trường học có sẵn hạ tầng mạng Internet ổn định tại các phòng ban và phòng học.
*   **AS-2:** Giả định rằng Ban giám hiệu cam kết thúc đẩy việc sử dụng phần mềm thay thế hoàn toàn sổ sách giấy (không chạy song song 2 hệ thống quá lâu).
*   **DE-1:** Dự án phụ thuộc vào danh sách mã tài sản, danh sách phòng ban/nhân sự chính xác được cung cấp bởi phòng Hành chính trước khi khởi chạy.

## 2. SCOPE AND LIMITATIONS (PHẠM VI VÀ GIỚI HẠN)

### 2.1. Major Features (Các tính năng chính)
Hệ thống được tổ chức thành 6 nhóm tính năng chính:

*   **FE-1: Quản lý Danh mục (Category Management)**
    *   Tạo, cập nhật và quản lý các nhóm/loại tài sản chuẩn (ví dụ: Bàn ghế, Máy tính, Thiết bị thí nghiệm).
    *   Thiết lập quy tắc mã hóa tự động cho từng loại danh mục.

*   **FE-2: Quản lý Tài sản (Asset Management)**
    *   Ghi nhận tài sản mới với đầy đủ thông tin (Model, Serial, Giá trị, Nhà cung cấp).
    *   Cập nhật trạng thái vòng đời: Mới -> Đang dùng -> Hỏng/Bảo trì -> Thanh lý.
    *   Tra cứu và xem lịch sử chi tiết của từng tài sản.

*   **FE-3: Quy trình Mua sắm (Acquisition)**
    *   Gửi yêu cầu cấp phát từ Giáo viên/Trưởng bộ môn.
    *   Tự động kiểm tra tồn kho.
    *   Tạo và phê duyệt Đề xuất mua sắm (nếu kho hết).

*   **FE-4: Điều chuyển Tài sản (Asset Transfer)**
    *   Quy trình điều chuyển chặt chẽ 4 bước: Tạo phiếu -> Kế toán duyệt -> Bàn giao (nguồn) -> Xác nhận nhận (đích).
    *   Theo dõi lịch sử luân chuyển để quy trách nhiệm.

*   **FE-5: Báo cáo & Thống kê (Reporting)**
    *   Dashboard cho Ban giám hiệu (Tổng quan tài sản, Ngân sách mua sắm).
    *   Báo cáo chi tiết cho Kế toán (Khấu hao, Kiểm kê, Thanh lý).

*   **FE-6: Chức năng chung (Common)**
    *   Đăng nhập/Đăng xuất bảo mật.
    *   Quản lý hồ sơ cá nhân và đổi mật khẩu.

### 2.2. Scope of Initial Release (Phạm vi phát hành ban đầu - Release 1.0)
Trong phiên bản đầu tiên (MVP), hệ thống sẽ tập trung vào các chức năng cốt lõi để vận hành quy trình cơ bản:

*   **Quản lý danh mục & Tài sản:** Hoàn thiện việc nhập liệu và quản lý trạng thái tài sản (FE-1, FE-2).
*   **Quy trình Điều chuyển:** Triển khai đầy đủ để kiểm soát việc mất mát tài sản (FE-4).
*   **Mua sắm cơ bản:** Cho phép tạo yêu cầu và duyệt yêu cầu cấp phát/mua sắm (FE-3).
*   **Báo cáo cơ bản:** Xuất danh sách tài sản và lịch sử sử dụng (Phần FE-5).
*   *Mục tiêu:* Thay thế hoàn toàn việc theo dõi tài sản bằng file Excel.

### 2.3. Scope of Subsequent Releases (Phạm vi các bản phát hành sau)
Các tính năng nâng cao sẽ được phát triển trong các giai đoạn tiếp theo (Release 2.0+):

*   **Tích hợp Barcode/QR Code:** Cho phép quét mã để kiểm kê nhanh trên thiết bị di động.
*   **Mô-đun Khấu hao tự động:** Tính toán giá trị còn lại của tài sản theo thời gian thực.
*   **Cảnh báo bảo trì định kỳ:** Tự động gửi email nhắc nhở bảo trì cho các thiết bị máy móc.
*   **Tích hợp API:** Kết nối với phần mềm Kế toán của nhà trường (nếu có).

### 2.4. Limitations and Exclusions (Giới hạn và Các phần loại trừ)
*   **EX-1 (Không quản lý tài chính chi tiết):** Hệ thống chỉ ghi nhận giá trị mua sắm ban đầu và duyệt đề xuất, KHÔNG thực hiện thanh toán, xuất hóa đơn hay hạch toán kế toán chi tiết.
*   **EX-2 (Không tương tác với Nhà cung cấp):** Hệ thống không gửi đơn hàng trực tiếp cho nhà cung cấp (Vendor). Việc liên hệ và mua hàng diễn ra bên ngoài hệ thống.
*   **EX-3 (Không hỗ trợ Offline):** Hệ thống yêu cầu kết nối Internet liên tục để hoạt động, không có chế độ offline-sync.
*   **EX-4 (Chưa hỗ trợ Mobile App):** Phiên bản đầu chỉ là Web Application, giao diện có thể Responsive nhưng chưa có App riêng cho iOS/Android.

